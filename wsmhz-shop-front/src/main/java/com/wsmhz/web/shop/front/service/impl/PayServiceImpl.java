package com.wsmhz.web.shop.front.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wsmhz.common.business.service.BaseServiceImpl;
import com.wsmhz.common.business.utils.DateTimeUtil;
import com.wsmhz.common.data.response.ServerResponse;
import com.wsmhz.web.shop.common.dao.PayInfoMapper;
import com.wsmhz.web.shop.common.dao.ProductMapper;
import com.wsmhz.web.shop.common.domain.Order;
import com.wsmhz.web.shop.common.domain.OrderItem;
import com.wsmhz.web.shop.common.domain.PayInfo;
import com.wsmhz.web.shop.common.domain.Product;
import com.wsmhz.web.shop.common.enums.OrderConst;
import com.wsmhz.web.shop.common.properties.BusinessProperties;
import com.wsmhz.web.shop.common.service.OrderService;
import com.wsmhz.web.shop.common.utils.BigDecimalUtil;
import com.wsmhz.web.shop.common.utils.FTPUtil;
import com.wsmhz.web.shop.front.service.PayService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * create by tangbj on 2018/5/29
 */
@Service
public class PayServiceImpl extends BaseServiceImpl<Order> implements PayService{

    private static AlipayTradeService tradeService;
    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);
    static {

        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        File file = null;
        try {
            InputStream stream = PayServiceImpl.class.getClassLoader().getResourceAsStream("zfbInfo.properties");
            File targetFile = new File("wsmhz-shop-front/src/main/resources/zfbInfo.properties");
            FileUtils.copyInputStreamToFile(stream, targetFile);
//            file = ResourceUtils.getFile("classpath:zfbInfo.properties");
            logger.info("支付宝配置文件新的路径："+targetFile.getAbsolutePath());
            Configs.init(targetFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }


    @Autowired
    private BusinessProperties businessProperties;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayInfoMapper payInfoMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse pay(Long orderNo, Long userId, String path) {
        Map<String ,String> resultMap = Maps.newHashMap();
        Order order = orderService.selectByUserIdAndOrderNo(userId,orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        resultMap.put("orderNo",String.valueOf(order.getOrderNo()));

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("wsmhzShop扫码支付,订单号:").append(outTradeNo).toString();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");


        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<>();

        List<OrderItem> orderItemList = orderService.selectByOrderItemNoAndUserId(orderNo,userId);
        for(OrderItem orderItem : orderItemList){
            GoodsDetail goods = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(),
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(),new Double(100).doubleValue()).longValue(),
                    orderItem.getQuantity());
            goodsDetailList.add(goods);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl("http://www.wsmhz.cn/api/order/aliPayCallback")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                File folder = new File(path);
                if(!folder.exists()){
                    folder.setWritable(true);
                    folder.mkdirs();
                }

                // 需要修改为运行机器上的路径
                //细节细节细节
                String qrPath = String.format(path+"/qr-%s.png",response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png",response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);

                File targetFile = new File(path,qrFileName);
                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                } catch (IOException e) {
                    logger.error("上传二维码异常",e);
                }
                logger.info("qrPath:" + qrPath);
                String qrUrl = businessProperties.getFtp().getHttpPrefix()+targetFile.getName();
                resultMap.put("qrCodeUrl",qrUrl);
                return ServerResponse.createBySuccess(resultMap);
            case FAILED:
                logger.error("支付宝预下单失败!!!");
                return ServerResponse.createByErrorMessage("支付宝预下单失败!!!");

            case UNKNOWN:
                logger.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!!!");

            default:
                logger.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }
    }

    @Override
    public ServerResponse aliPayCallback(Map<String, String> params) {
        Long orderNo = Long.parseLong(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        Order order = orderService.selectByUserIdAndOrderNo(null,orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("非本商城的订单,回调忽略");
        }
        if(order.getStatus().getCode() >= OrderConst.OrderStatusEnum.PAID.getCode()){
            return ServerResponse.createBySuccess("支付宝重复调用");
        }
        if(OrderConst.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(OrderConst.OrderStatusEnum.PAID);
            orderService.updateByPrimaryKeySelective(order);
        }

        // 支付平台支付信息持久化
        PayInfo payInfo = new PayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(OrderConst.PayPlatformEnum.ALIPAY);
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tradeStatus);

        payInfoMapper.insertSelective(payInfo);
        return ServerResponse.createBySuccess();
    }

    @Override
    public void closeOrder(int hour) {
        Date closeDateTime = DateUtils.addHours(new Date(),-hour);
        List<Order> orderList = orderService.selectByOrderStatusAndCreateDate(OrderConst.OrderStatusEnum.NO_PAY,closeDateTime);

        for(Order order : orderList){
            List<OrderItem> orderItemList = orderService.selectByOrderItemNoAndUserId(order.getOrderNo(),null);
            for(OrderItem orderItem : orderItemList){

                //一定要用主键where条件，防止锁表。同时必须是支持MySQL的InnoDB。
                Integer stock = productMapper.selectStockByProductId(orderItem.getProductId());

                //考虑到已生成的订单里的商品被删除的情况
                if(stock == null){
                    continue;
                }
                Product product = new Product();
                product.setId(orderItem.getProductId());
                product.setStock(stock+orderItem.getQuantity());
                productMapper.updateByPrimaryKeySelective(product);
            }
            closeOrderByOrderId(order.getId());
            logger.info("关闭订单OrderNo：{}",order.getOrderNo());
        }
    }

    private void closeOrderByOrderId(Long id){
        Order order = new Order();
        order.setId(id);
        order.setStatus(OrderConst.OrderStatusEnum.ORDER_CLOSE);
        orderService.updateByPrimaryKeySelective(order);
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }
}
