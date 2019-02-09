/**
 * 模态窗口
 */
var callback;
window.Modal = {
    tpls:{
        alert:'<div class="am-modal am-modal-alert" tabindex="-1" data-backdrop="static"><div class="am-modal-dialog"><div class="am-modal-hd message-title"></div><div class="am-modal-bd message-text"></div><div class="am-modal-footer"><span class="am-modal-btn">确定</span></div></div></div>',
        confirm:'<div class="am-modal am-modal-confirm" tabindex="-1" data-backdrop="static"><div class="am-modal-dialog"><div class="am-modal-bd message-text"></div><div class="am-modal-footer"><span class="am-modal-btn btn-confirm"data-am-modal-confirm>确定</span><span class="am-modal-btn btn-cancel"data-am-modal-cancel>取消</span></div></div></div>',
        loading:'<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" data-backdrop="static"><div class="am-modal-dialog"><div class="am-modal-hd message-text">正在载入...</div><div class="am-modal-bd"><span class="am-icon-spinner am-icon-spin"></span></div></div></div>'
    },
    register:function(events){
        events = events || [];
        if(events.length == 0){
            events = ['alert', 'confirm', 'loading'];
        }
        var body = $(document.body);
        this.modal = {};
        for(var i=0; i<events.length; i++){
            var event = events[i];
            var tpl = this.tpls[event];
            if(tpl){
                this.modal[event] = $(tpl);
                body.append(this.modal[event]);
            }
        }
    },
    alert:function(messageTitle,messageText,fn){
        var param = arguments;
        // 参数解析 如果个数为一个 则默认提示标题
        if(param.length==1){
            messageText = messageTitle;
            messageTitle = '提示';
        }
        if(param.length==2){    // 个数为2 需要判断第二个参数类型是否为函数
            if(typeof param[1] == "function"){   // 默认标题
                fn = messageText;
                messageText = messageTitle;
                messageTitle = '提示';
            }
        }
        this.modal.alert.find(".am-modal-btn").text("确定");
        this.modal.alert.find(".message-title").html(messageTitle);
        this.modal.alert.find(".message-text").html(messageText);
        this.modal.alert.modal({'closeViaDimmer':false}).modal("open");
        if(typeof fn == "function"){
            callback = fn;
            this.modal.alert.off('close.modal.amui');
            this.modal.alert.on('close.modal.amui',function(){
                callback.call();
            })
        }
    },
    confirm:function(message,fn){
        var confirmText = "确定";
        var cancelText = "取消";
        this.modal.confirm.find(".btn-confirm").text(confirmText);
        this.modal.confirm.find(".btn-cancel").text(cancelText);
        this.modal.confirm.find(".message-text").html(message);
        if(typeof fn == "function"){
            callback = fn;
            this.modal.confirm.modal({'closeViaDimmer':false,'onConfirm':function(){
                    callback.call();
                }
            }).modal("open");
        }
    },
    loading:function(message){
        this.modal.loading.find(".message-text").html(message);
        this.modal.loading.modal({'closeViaDimmer':false}).modal("open");
    },
    closeloading:function(){
        this.modal.loading.find(".message-text").html("");
        this.modal.loading.modal("close");
    }
};
$().ready(function(){
    Modal.register();
});
