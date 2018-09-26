$(function () {
    "use strict";

    function getContextPath() {
        var pathName = document.location.pathname;
        var index = pathName.substr(1).indexOf("/");
        var host = pathName.substr(0, index + 1);
    }

    function getHost() {
        var host = window.location.host;
        if (host.indexOf(":") == -1) {
            return host;
        } else {
            var index = host.indexOf(":");
            return host.substr(0, index + 1);
        }
    }

    var header = $('#header');
    var content = $('#content');
    var input = $('#input');
    var status = $('#status');
    var myName = false;
    var author = null;
    var logged = false;
    var socket = io.connect(getHost() + "9090");

    function connect() {
        socket.on('reconnect', function () {
            update("reconnect")
        });
        socket.on('reconnecting', function (nextRetry) {
            update("reconnecting")
        });
        socket.on('reconnect_failed', function () {
            update("reconnect_failed")
        });
    }

    //监听服务器连接事件
    socket.on('connect', function () {
        input.removeAttr('disabled').focus();
        status.text('Choose name:');
        update("Connected to Server");
    });
    //监听服务器关闭服务事件
    socket.on('disconnect', function () {
        update("Disconnected from Server");
    });
    //监听服务器端发送消息事件
    socket.on('chat', function (data) {
        message(data)
        //console.log("服务器发送的消息是："+data);
    });

    //断开连接
    function disconnect() {
        socket.disconnect();
    }

    function update(status) {
        content.html($('<p>', {
            text: status
        }));
    }

    function message(data) {
        var json = JSON.parse(data);
        input.removeAttr('disabled').focus();
        if (!logged && myName) {
            logged = true;
            status.text(myName + ': ').css('color', 'blue');
        } else {
            var me = json.author == author;
            var date = typeof (json.time) == 'string' ? parseInt(json.time)
                : json.time;
            addMessage(json.author, json.message, me ? 'blue' : 'black',
                new Date(date));
        }
    }

    //发送消息
    input.keydown(function (e) {
        if (e.keyCode === 13) {
            var msg = $(this).val();

            // First message is always the author's name
            if (author == null) {
                author = msg;
            }
            var messageData = JSON.stringify({
                author: author,
                message: msg
            });
            socket.emit('chat', {msgContent: messageData});
            $(this).val('');

            input.attr('disabled', 'disabled');
            if (myName === false) {
                myName = msg;
            }
        }
    });

    //显示聊天记录
    function addMessage(author, message, color, datetime) {
        content.append('<p><span style="color:'
            + color
            + '">'
            + author
            + '</span> @ '
            + +(datetime.getHours() < 10 ? '0' + datetime.getHours()
                : datetime.getHours())
            + ':'
            + (datetime.getMinutes() < 10 ? '0' + datetime.getMinutes()
                : datetime.getMinutes()) + ': ' + message + '</p>');
    }

});
