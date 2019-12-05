package com.iqianjin.appperformance.config;

import java.util.*;

public class ElementTypeEnum {
    public static final Map<String, List<String>> mapAndroid = new HashMap();
    public static final Map<String, List<String>> mapIos = new HashMap();

    static {
        /*****************************  android *****************************/
        //开机广告
        mapAndroid.put("开机广告", Arrays.asList("xpath://*[contains(@text,'跳过')]"));
        //开始使用爱钱进
        mapAndroid.put("开始使用爱钱进", Arrays.asList("id:guideBottomStartTv"));
        mapAndroid.put("跳过", Arrays.asList("id:guideStartBtn"));
        //版本升级
        mapAndroid.put("取消升级", Arrays.asList("xpath://*[@text='取消']"));

        //首页元素
        mapAndroid.put("首页tab", Arrays.asList("id:tab1Iv"));
        mapAndroid.put("产品tab", Arrays.asList("id:tab2Iv"));
        mapAndroid.put("发现tab", Arrays.asList("id:tab3Iv"));
        mapAndroid.put("我的tab", Arrays.asList("id:tab4Iv"));

        //登录相关
        mapAndroid.put("其他登录", Arrays.asList("id:registOtherLoginLl"));
//        mapAndroid.put("用户名", "id:userNameEt");
        mapAndroid.put("用户名", Arrays.asList("xpath://*[@text='请输入手机号/用户名/邮箱']"));
        mapAndroid.put("密码", Arrays.asList("id:passwdStatusEt"));
        mapAndroid.put("登录按钮", Arrays.asList("id:loginSubmitTv"));
        mapAndroid.put("忘记手势密码", Arrays.asList("id:gestureForget"));
        mapAndroid.put("请绘制解锁图案", Arrays.asList("xpath://*[@text='请绘制解锁图案']"));
        mapAndroid.put("重新登录", Arrays.asList("xpath://*[@text='重新登录']"));
        mapAndroid.put("设置按钮", Arrays.asList("id:tvSetting"));
        mapAndroid.put("退出登录", Arrays.asList("xpath://*[@text='退出登录']"));
        mapAndroid.put("确认按钮", Arrays.asList("id:dialogARightButton"));
        //注册
        mapAndroid.put("注册按钮", Arrays.asList("id:loginBackRegistTv"));
        mapAndroid.put("注册下一步", Arrays.asList("id:registSubmitTv"));
        mapAndroid.put("注册页提示文案", Arrays.asList("id:registDescTv"));
        //出借记录
        mapAndroid.put("出借记录", Arrays.asList("xpath://*[@text='出借记录']"));
        mapAndroid.put("出借记录-爱盈宝", Arrays.asList("xpath://*[@text='爱盈宝']"));
        mapAndroid.put("出借记录-月进宝", Arrays.asList("xpath://*[@text='月进宝']"));
        mapAndroid.put("整存宝+", Arrays.asList("xpath://*[@text='整存宝+']"));
        mapAndroid.put("散标", Arrays.asList("xpath://*[@text='散标']"));
        mapAndroid.put("转让中", Arrays.asList("xpath://*[@text='转让中']"));
        mapAndroid.put("已转让", Arrays.asList("xpath://*[@text='已转让']"));
        mapAndroid.put("已结束", Arrays.asList("xpath://*[@text='已结束']"));
        mapAndroid.put("散标出借记录期号", Arrays.asList("id:record_invert_item_issue"));
        //购买爱盈宝
        mapAndroid.put("爱盈宝", Arrays.asList("xpath://*[@text='爱盈宝·到期转让']", "xpath://*[@text='爱盈宝']"));
        mapAndroid.put("三月期立即投资按钮", Arrays.asList("xpath://*[@text='3']"));
        mapAndroid.put("金额输入框", Arrays.asList("id:join_product_edit_text"));
        mapAndroid.put("购买页立即投资按钮", Arrays.asList("id:join_product_confirm"));
        mapAndroid.put("弹框确认投资按钮", Arrays.asList("id:confirmBuySubmit"));
        mapAndroid.put("购买成功页完成按钮", Arrays.asList("id:submit"));
        //月进宝
        mapAndroid.put("月进宝", Arrays.asList("xpath://*[@text='月进宝·月回本息']", "xpath://*[@text='月进宝']"));
        mapAndroid.put("月进宝立即投资", Arrays.asList("xpath://*[@text='3']", "id:productItemBtn"));
        mapAndroid.put("月进宝金额输入框", Arrays.asList("id:etAmount"));
        mapAndroid.put("月进宝立即投资按钮", Arrays.asList("id:joinProductConfirm"));
        //我的-资金流水
        mapAndroid.put("资金流水", Arrays.asList("xpath://*[@text='资金流水']"));
        mapAndroid.put("充值", Arrays.asList("xpath://*[@text='充值']"));
        mapAndroid.put("提现", Arrays.asList("xpath://*[@text='提现']"));
        mapAndroid.put("出借", Arrays.asList("xpath://*[@text='出借']"));
        mapAndroid.put("回收", Arrays.asList("xpath://*[@text='回收']"));
        mapAndroid.put("转让债权", Arrays.asList("xpath://*[@text='转让债权']"));
        mapAndroid.put("冻结", Arrays.asList("xpath://*[@text='冻结']"));
        mapAndroid.put("资金流水日期", Arrays.asList("id:tvFlowTime"));
        //我的-出借回报资产等
        mapAndroid.put("昨日出借回报", Arrays.asList("id:tvYesterdayEarnings"));
        mapAndroid.put("累计出借回报", Arrays.asList("id:tvTotalProfit"));
        mapAndroid.put("我的资产", Arrays.asList("id:tvTotalAssets"));
        //我的-优惠劵、奖励劵
        mapAndroid.put("优惠劵", Arrays.asList("id:tvRedBagAmount"));
        mapAndroid.put("奖励劵", Arrays.asList("id:tvAddInterestNum"));
        mapAndroid.put("查看过期优惠劵", Arrays.asList("id:tv_coupon_new_expired", "id:tv_reward_new_expired"));
        mapAndroid.put("查看过期奖励劵", Arrays.asList("id:tv_coupon_new_expired", "id:tv_reward_new_expired"));
        mapAndroid.put("优惠劵弹窗-知道了按钮", Arrays.asList("id:tv_coupon_guide_confirm"));
        mapAndroid.put("奖励劵弹窗-知道了按钮", Arrays.asList("id:tv_add_interest_guide_confirm"));

        /***************************** ios *****************************/
        //doraemon悬浮窗
        mapIos.put("doraemon悬浮窗", Arrays.asList("pre:type == 'XCUIElementTypeButton' AND name == 'doraemon logo@3x'"));
        mapIos.put("doraemon自定义", Arrays.asList("pre:type == 'XCUIElementTypeStaticText' AND name == '自定义'"));
        mapIos.put("doraemon数据采集按钮", Arrays.asList("pre:type == 'XCUIElementTypeButton' AND name CONTAINS  '数据采集'"));
        mapIos.put("doraemon清除本地数据1", Arrays.asList("id:清除性能数据"));
        mapIos.put("doraemon清除本地数据2", Arrays.asList("pre:type == 'XCUIElementTypeStaticText' AND name ENDSWITH[C] 'M' OR  name ENDSWITH[C] 'kb'"));
        mapIos.put("doraemon确定按钮", Arrays.asList("pre:type == 'XCUIElementTypeButton' AND name == '确定'"));
        // 悬浮窗切换环境
        mapIos.put("笑脸", Arrays.asList("pre:type = 'XCUIElementTypeImage' AND name ENDSWITH[C] 'weixiao'"));
        mapIos.put("环境", Arrays.asList("pre:type = 'XCUIElementTypeButton' AND name == '环境'"));
        mapIos.put("环境输入框", Arrays.asList("pre:type = 'XCUIElementTypeTextField'"));
        mapIos.put("确定替换服务器", Arrays.asList("pre:type = 'XCUIElementTypeButton' and name =='确定替换服务器IP'"));
        //开机广告
        mapIos.put("开机广告", Arrays.asList("pre:type == 'XCUIElementTypeButton' AND name CONTAINS '跳过'"));
        mapIos.put("开始使用爱钱进", Arrays.asList("id:开始使用爱钱进"));
        mapIos.put("跳过", Arrays.asList("id:跳过"));
        //版本升级
        mapIos.put("取消升级", Arrays.asList("pre:type == 'XCUIElementTypeButton' AND name == '取消'"));
        //首页元素
        mapIos.put("首页tab", Arrays.asList("id:首页"));
        mapIos.put("产品tab", Arrays.asList("id:产品"));
        mapIos.put("发现tab", Arrays.asList("id:发现"));
        mapIos.put("我的tab", Arrays.asList("id:我的"));
        //登录相关
        mapIos.put("其他登录", Arrays.asList("id:其他登录"));
        mapIos.put("用户名", Arrays.asList("id:请输入手机号码/用户名/邮箱"));
        mapIos.put("密码", Arrays.asList("id:请输入登录密码"));
        mapIos.put("登录按钮", Arrays.asList("id:登录"));
        mapIos.put("忘记手势密码", Arrays.asList("id:忘记手势密码"));
        mapIos.put("请绘制解锁图案", Arrays.asList("id:请绘制解锁图案"));
        mapIos.put("重新登录", Arrays.asList("id:重新登录"));
        mapIos.put("设置按钮", Arrays.asList("id:设置"));
        mapIos.put("退出登录", Arrays.asList("id:退出登录"));
        mapIos.put("确认按钮", Arrays.asList("id:确认"));
        //出借记录
        mapIos.put("出借记录", Arrays.asList("id:出借记录"));
        mapIos.put("出借记录-爱盈宝", Arrays.asList("id:爱盈宝"));
        mapIos.put("出借记录-月进宝", Arrays.asList("id:月进宝"));
        mapIos.put("整存宝+", Arrays.asList("id:整存宝+"));
        mapIos.put("散标", Arrays.asList("id:散标"));
        mapIos.put("转让中", Arrays.asList("id:转让中"));
        mapIos.put("已转让", Arrays.asList("id:已转让"));
        mapIos.put("已结束", Arrays.asList("id:已结束"));
        mapIos.put("没有更多", Arrays.asList("id:没有更多"));
        mapIos.put("散标出借记录期号", Arrays.asList("id:I17N03003"));
        //购买爱盈宝
        mapIos.put("爱盈宝", Arrays.asList("pre:type == 'XCUIElementTypeStaticText' and name BEGINSWITH '爱盈宝'", "id:爱盈宝"));
        mapIos.put("三月期立即投资按钮", Arrays.asList("pre:type == 'XCUIElementTypeStaticText' AND name == '3'", "xpath://XCUIElementTypeStaticText[@name='3']"));
        mapIos.put("金额输入框", Arrays.asList("pre:type == 'XCUIElementTypeTextField' ", "xpath://XCUIElementTypeTextField"));
        mapIos.put("购买页立即投资按钮", Arrays.asList("id:立即投资"));
        mapIos.put("弹框确认投资按钮", Arrays.asList("id:确认投资"));
        mapIos.put("购买成功页完成按钮", Arrays.asList("pre:type == 'XCUIElementTypeButton' AND name='完成'"));
        //月进宝
        mapIos.put("月进宝", Arrays.asList("pre:type == 'XCUIElementTypeStaticText' and name BEGINSWITH '月进宝'", "id:月进宝"));
        mapIos.put("月进宝立即投资", Arrays.asList("pre:type == 'XCUIElementTypeStaticText' and name ='3'", "id:立即投资"));
        mapIos.put("月进宝金额输入框", Arrays.asList("pre:type == 'XCUIElementTypeTextField' ", "xpath://XCUIElementTypeTextField"));
        mapIos.put("月进宝立即投资按钮", Arrays.asList("id:立即投资"));
        //我的-资金流水
        mapIos.put("资金流水", Arrays.asList("pre:type == 'XCUIElementTypeButton' AND name='资金流水'", "xpath://*[@name='资金流水']"));
        mapIos.put("充值", Arrays.asList("id:充值"));
        mapIos.put("提现", Arrays.asList("id:提现"));
        mapIos.put("出借", Arrays.asList("id:出借"));
        mapIos.put("回收", Arrays.asList("id:回收"));
        mapIos.put("转让债权", Arrays.asList("id:转让债权"));
        mapIos.put("冻结", Arrays.asList("id:冻结"));
        mapIos.put("资金流水日期", Arrays.asList("xpth://XCUIElementTypeStaticText"));
        //我的-出借回报资产等
        mapIos.put("昨日出借回报", Arrays.asList("id:昨日出借回报(元)"));
        mapIos.put("累计出借回报", Arrays.asList("id:累计出借回报(元)"));
        mapIos.put("我的资产", Arrays.asList("id:我的资产(元)"));
        //我的-优惠劵、奖励劵
        mapIos.put("优惠劵", Arrays.asList("id:可用优惠券(元)"));
        mapIos.put("奖励劵", Arrays.asList("id:可用奖励券(张)"));
        mapIos.put("查看过期优惠劵", Arrays.asList("id:查看过期优惠券"));
        mapIos.put("查看过期奖励劵", Arrays.asList("id:查看过期奖励券"));
        mapIos.put("优惠劵弹窗-知道了按钮", Arrays.asList("id:知道了"));
        mapIos.put("奖励劵弹窗-知道了按钮", Arrays.asList("id:知道了"));
    }


    /**  ios 使用xpath定位很慢，每次运行XPath查询时，必须递归遍历整个应用程序层次结构并将其序列化为XML。
     这本身就会花费很多时间，尤其是如果你的应用有很多元素的话。然后，在此XML序列化上运行XPath查询之后，
     必须将匹配元素列表反序列化为实际的元素对象，然后将它们的WebDriver表示返回给客户机调用
     https://github.com/facebookarchive/WebDriverAgent/wiki/Predicate-Queries-Construction-Rules
     String selector = "type == 'XCUIElementTypeButton' AND value BEGINSWITH[c] 'bla' AND visible == 1";
     String selector = "type == 'XCUIElementTypeStaticText' AND value BEGINSWITH[c] '3'";
     **/
}
