package com.qingke.apiplatform.weixin.handler;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qingke.apiplatform.model.CodeMessage;
import com.qingke.apiplatform.services.CodeMessageService;
import com.qingke.apiplatform.weixin.builder.TextBuilder;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class SubscribeHandler extends AbstractHandler {
	
	@Autowired
	private CodeMessageService codeMsgService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

//        // 获取微信用户基本信息
//        try {
//            WxMpUser userWxInfo = weixinService.getUserService()
//                .userInfo(wxMessage.getFromUser(), null);
//            if (userWxInfo != null) {
//                // TODO 可以添加关注用户到本地数据库
//            }
//        } catch (WxErrorException e) {
//            if (e.getError().getErrorCode() == 48001) {
//                this.logger.info("该公众号没有获取用户信息权限！");
//            }
//        }


        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("欢迎您选择轻客众创空间！\n\n1、获得企业邀请二维码扫码关注的用户，请点击【成为轻客】，进行注册。"
            		+ "\n\n2、未获得企业邀请二维码，无法完成注册，请联系合作企业获取邀请二维码；"
            		+ "\n\n如在注册过程中遇到问题，请联系：0539-7426198\n", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
        throws Exception {
    	if(StringUtils.isBlank(wxMessage.getEventKey()))
    		return null;
    	
    	CodeMessage codeMessage = new CodeMessage();
    	codeMessage.setCreateTime(wxMessage.getCreateTime());
    	codeMessage.setEventType(wxMessage.getEvent());
    	codeMessage.setEvnetKey(wxMessage.getEventKey().replace("qrscene_", ""));
    	codeMessage.setFromUsername(wxMessage.getFromUser());
    	codeMessage.setToUsername(wxMessage.getToUser());
    	codeMessage.setTicket(wxMessage.getTicket());
    	codeMessage.setMsgType(wxMessage.getMsgType());
    	
    	codeMsgService.insert(codeMessage);
        //TODO
        return null;
    }

}
