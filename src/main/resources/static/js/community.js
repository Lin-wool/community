/**
 * 提交回复
 */
function post() {
    var parentId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2Target(parentId, 1, content);
}

/**
 * 封装评论请求
 */

function comment2Target(targetId, type, content) {
    if (!content || content.trim() == "") {
        alert("回复内容不能为空~~~");
        return;
    }
    $.ajax({
        type: "post",
        url: "/comment",
        data: JSON.stringify({"parentId": targetId, "content": content, "type": type}),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data.code == 200) {
                window.location.reload();
            } else {
                if (data.code == 2003) {
                    var conf = confirm(data.message);
                    if (conf) {
                        window.open("https://github.com/login/oauth/authorize?client_id=72d4490a6a78007d0d4b&redirect_uri=http://localhost:7001/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    } else {
                        alert(data.message);
                    }
                }

            }
        }
    })
}

/**
 * 回复二级评论
 */

function comment(e) {
    var id = $(e).attr("commentId");
    var content = $("#input-" + id).val();
    comment2Target(id, 2, content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = $(e).attr("commentId");
    var comments = $("#comment-" + id);
    if (comments.hasClass("in")) {
        comments.removeClass("in");
        $(e).removeClass("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            comments.addClass("in");
            $(e).addClass("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm:ss')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                $(e).addClass("active");
            });
        }


    }
}


function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}