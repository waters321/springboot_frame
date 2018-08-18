<%@ page language="java"  pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Cache-Control" content="max-age=0"/>
<meta name="HandheldFriendly" content="true" />
<meta name="viewport" content="width=320.1, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link href="${itx}/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${itx}/css/music.css">
<script type="text/javascript" src="${itx}/js/jquery.js"></script>
<script language="javascript" type="text/javascript" src="${itx}/js/jquery.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js" ></script>
<script language="javascript" type="text/javascript" src="${itx}/js/common.js"></script>
<script language="javascript" type="text/javascript" src="${itx}/js/home.js"></script>
<script type="text/javascript">const siteUrl="./",musicMp3="${itx}/sound/bgm.mp3"</script>
<script type="text/javascript" src="${itx}/js/music.js"></script>
<script>
    var commonShareUrl = '${shareUrl}';
    var ctx = '${ctx}';
    var itx = 'https://nwk.oss-cn-hangzhou.aliyuncs.com/nwk-sleep-god';
    var share_type_mark = 0; //0：代表常规分享 ,1:代表拉票分享
    var type = 0; //0：代表常规分享 ,1:代表拉票分享
    var wxTitle = '睡神驾到，为睡神点赞！';
    var wxDesc = '史上睡姿最奇葩的宠物，我为TA疯狂打CALL！快来投TA一票！';
    var wxShareImg = 'https://nwk.oss-cn-hangzhou.aliyuncs.com/nwk-sleep-god/share_dog.jpg';
   	var wxShareUrl = '${shareUrl}?share_openid='+'${weChatInfo.openid}'+'&type='+type;
	var postAjaxUrl = '${ctx}/addNwkShareLog?share_type_mark='+share_type_mark;
    $(document).ready(function () {
        wxReady();
    });

    var imgUrl = null;
	var videoUrl = null;

    function addImages(url) {
        imgUrl = url;
       /*  $('#uploadVideo').remove(); */
       $('#upload_container ').empty();
       $('#upload_container ').append('<img  src="" onerror="smallImgError(this)" id="uploadImg" style="width: 100%; height: 100%;" />')
        $('#uploadImg').attr('src',imgUrl);
        /* $('#uploadImg').show(); */
        
        $('.pop_bg').height($(document).height());
    }
	function addVideo(url){
		/* if($("#upload_container").find("video").length == 0 ){ */
		 	$('#upload_container ').empty();
			/* $('#upload_container ').append('<video src="" id="uploadVideo" style="width: 100%; height: 100%;"></video>') */
			$('#upload_container ').append('<img src="" onerror="smallImgError(this)" id="uploadVideo" style="width: 100%; height: 100%;">')
			$('#upload_container ').append('<video src=""  id="uploadVideo_1" style="display:none; width: 100%; height: 100%;">')
		/* } */

		/* $("#uploadImg").css('display','none');  */
		videoUrl = url+'?x-oss-process=video/snapshot,t_1000,f_jpg,w_260,h_180,m_fast';
		$('#uploadVideo').attr('src',videoUrl);
		$('#uploadVideo_1').attr('src',url);
        $('.pop_bg').height($(document).height());
	}

    /**
     * 微信分享回调函数
     * result：微信分享结果
     */
    var wxShareCallbackFun = function(result) {
        $.post(postAjaxUrl,{wxShareResultJson:JSON.stringify(result)})
    };

    /**
     * 微信分享结果实体
     * shareType：分享类型
     * shareCode：分享结果CODE
     */
    function WxShareResult(shareType,shareCode) {
        var obj = {};
        obj.shareType = shareType;
        obj.shareResult = shareCode;
        obj.shareUrl = wxShareUrl;
        return obj;
    }

    /**
     * 分享类型
     * APP_MESSAGE：微信消息分享
     * TIMELINE：微信朋友圈分享
     * QQ：QQ消息分享
     * QZONE：QQ控
     * @type {{APP_MESSAGE: string, TIMELINE: string, QQ: string, QZONE: string}}
     */
    var wxShareType =  {APP_MESSAGE : 'APP_MESSAGE', TIMELINE : 'TIMELINE', QQ : 'QQ', QZONE : 'QZONE'};

    /**
     * 分享结果
     * SUCCESS：成功
     * CANCEL：取消
     * @type {{SUCCESS: string, CANCEL: string}}
     */
    var wxResultCode = {SUCCESS : 'SUCCESS',CANCEL : 'CANCEL'};

    /**
     * 微信初始化
     */
    function wxReady() {
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: '${appid}', // 必填，公众号的唯一标识
            timestamp: '${timestamp}', // 必填，生成签名的时间戳
            nonceStr: '${noncestr}', // 必填，生成签名的随机串
            signature: '${signature}',// 必填，签名，见附录1
            jsApiList: [ 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ','onMenuShareQZone']
        });

        wx.ready(function () {
            //分享给朋友
            wx.onMenuShareAppMessage({
                title: wxTitle, // 分享标题
                desc: wxDesc, // 分享描述
                link: wxShareUrl, // 分享链接
                imgUrl: wxShareImg, // 分享图标
                type: '', // 分享类型,music、video或link，不填默认为link
                dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                success: function () {
                    wxShareCallbackFun(new WxShareResult(wxShareType.APP_MESSAGE,wxResultCode.SUCCESS));
                },
                cancel: function () {
                    wxShareCallbackFun(new WxShareResult(wxShareType.APP_MESSAGE,wxResultCode.CANCEL))
                }
            });

            //分享到朋友圈*****************************************************************************************************
            wx.onMenuShareTimeline({
                title: wxTitle,
                desc: wxDesc,
                link: wxShareUrl,
                imgUrl: wxShareImg,
                success: function () {
                    wxShareCallbackFun(new WxShareResult(wxShareType.TIMELINE,wxResultCode.SUCCESS))
                },
                cancel: function () {
                    wxShareCallbackFun(new WxShareResult(wxShareType.TIMELINE,wxResultCode.CANCEL))
                }
            });

            //分享到QQ*****************************************************************************************************
            wx.onMenuShareQQ({
                title: wxTitle,
                desc: wxDesc,
                link: wxShareUrl,
                imgUrl: wxShareImg,
                success: function () {
                    wxShareCallbackFun(new WxShareResult(wxShareType.QQ,wxResultCode.SUCCESS))
                },
                cancel: function () {
                    wxShareCallbackFun(new WxShareResult(wxShareType.QQ,wxResultCode.CANCEL))
                }
            });


            //分享到QQ空间*****************************************************************************************************
            wx.onMenuShareQZone({
                title: wxTitle,
                desc: wxDesc,
                link: wxShareUrl,
                imgUrl: wxShareImg,
                success: function () {
                    wxShareCallbackFun(new WxShareResult(wxShareType.QZONE,wxResultCode.SUCCESS))
                },
                cancel: function () {
                    wxShareCallbackFun(new WxShareResult(wxShareType.QZONE,wxResultCode.CANCEL))
                }
            });
        });
    }
    function smallImgError(img){
        img.src="https://nwk.oss-cn-hangzhou.aliyuncs.com/nwk-sleep-god/small_img.jpg";
        img.onerror=null;   //控制不要一直跳动
    }
</script>
