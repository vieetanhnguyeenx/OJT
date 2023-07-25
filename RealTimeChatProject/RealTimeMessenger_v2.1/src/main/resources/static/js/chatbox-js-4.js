function scrollToBottum() {
    var chatBox = document.getElementById('chatBoxMessage');
    chatBox.scrollTop = chatBox.scrollHeight;
}


//const searchForChat

window.onload = function () {
    scrollToBottum();
    document.getElementById("searchBar").addEventListener('click', searchActivated);

    function searchInactivated() {
        document.getElementById('searchBarIcon').classList.remove('la-times');
        document.getElementById('searchBarIcon').classList.add('la-search');
        document.getElementById('archivedMessage').classList.remove('display-none-for-div');
        document.getElementById('searchList').classList.add('display-none-for-div');
        document.getElementById('roomList').classList.remove('display-none-for-div');
        document.getElementById('searchBarIcon').removeEventListener("click", searchInactivated);
        document.getElementById('searchInputTag').value = '';
        setTimeout(function () {
            document.getElementById('searchBar').addEventListener('click', searchActivated);
        }, 10);
    }

    function searchActivated() {
        console.log("doi den");
        document.getElementById('searchBarIcon').classList.remove('la-search');
        document.getElementById('searchBarIcon').classList.add('la-times');
        document.getElementById('archivedMessage').classList.add('display-none-for-div');
        document.getElementById('searchList').classList.remove('display-none-for-div');
        document.getElementById('roomList').classList.add('display-none-for-div');
        document.getElementById('searchBarIcon').addEventListener("click", searchInactivated);
        document.getElementById('searchBar').removeEventListener('click', searchActivated);
        document.getElementById('searchInputTag').addEventListener('input',);
    }
};

(function ($) {
    //var position = $(".chat-search").last().position().top;
    //$(".scrollable-chat-panel").scrollTop(position);
    //$(".pagination-scrool").niceScroll();
    $(".chat-upload-trigger").on("click", function (e) {
        $(this).parent().find(".chat-upload").toggleClass("active");
    });
    $(".user-detail-trigger").on("click", function (e) {
        $(this).closest(".chat").find(".chat-user-detail").toggleClass("active");
    });
    $(".user-undetail-trigger").on("click", function (e) {
        $(this).closest(".chat").find(".chat-user-detail").toggleClass("active");
    });
})(jQuery);


// URL
const url = 'http://localhost:8888';


//Token
const token = localStorage.getItem("userToken");
console.log("token:" + token);

//UserId
const userId = JSON.parse(atob(token)).userId;
console.log("userId: " + userId);

//All Room
var allChatRoom;
var page = 1;
var dateToGenerateChat = "";
var chatInSelectedRoom = "";
const loadingHtml = '<div id="loadingElement" class="svg36 loader-animate3 horizontal-margin-auto mb-2"></div>'
var lastMaxHeight = -1;
var alpha = -1;
const userRoom = new Map();


var isExitsNewPage = true;

// Cache RoomMessage
const chatRoomMessage = new Map();

//Selected Room
var selectedRoom = -1;

// Month
const shortmonthInYear = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

// RealetedUserMap
const RealetedUserMap = new Map();


function generateMessage() {
    $('#chatBoxMessage').html(chatInSelectedRoom);
    if (page == 1) {
        scrollToBottum();
    } else {
        if (isExitsNewPage) {
            var element = document.getElementById("chatBoxMessage");
            console.log("lastMaxHeight " + lastMaxHeight + "thisMaxHeight " + element.scrollHeight);
            element.scrollTop = (element.scrollHeight - lastMaxHeight);
            // if (page == 2) {
            //     element.scrollTop = lastMaxHeight;
            // } else {
            //     element.scrollTop = lastMaxHeight - 800 * (page);
            // }

        }
    }
}

function padTo2Digits(num) {
    return num.toString().padStart(2, '0');
}

function formatDate(date) {
    var millisecondsString = date.getMilliseconds() + "";
    var firstTwoChars = millisecondsString;
    return (
        [
            date.getFullYear(),
            padTo2Digits(date.getMonth() + 1),
            padTo2Digits(date.getDate()),
        ].join('-') +
        ' ' +
        [
            padTo2Digits(date.getHours()),
            padTo2Digits(date.getMinutes()),
            padTo2Digits(date.getSeconds()),
        ].join(':') + '.' + millisecondsString.slice(0, 2)

    );
}

/*
    *** Function For DateTime to add zero when it < 10 ***
    EX: 3:1PM -> 03:01PM
*/

function addZeroToAnyThing(i) {
    if (i < 10) {
        i = "0" + i
    }
    return i;
}


/*
*** Function about Model ***
*/
function popupChatTitleModel() {
    $("#chatTitleModal").modal("show");
}

function popupChangeAliasNameModel() {
    $("#changeAliasNameModel").modal("show");
}

/*
    *** Funtion to set All User Profile ***

*/
function setAllUserProfile() {
    var loginUserProfile = JSON.parse(atob(token));
    $("#loginUserAvatar").html('<img class="user-detail-trigger rounded-circle shadow avatar-sm mr-3 chat-profile-picture" src="' + loginUserProfile.avatar + '"/>');
    $("#loginUserAvatarModel").html('<img src="' + loginUserProfile.avatar + '" class="rounded-circle img-fluid shadow avatar-xxl" />');
    $('#loginUsername').html(loginUserProfile.username);
    $('#loginAliasname').html(loginUserProfile.identifyCode);
}

/*
    ***Change Selected Room***
    1. Remove active class in last selected room id. Then add active class in new selected and change the seletedRoomId
*/

function changeActive(id) {
    if (selectedRoom != -1) {
        document.getElementById('roomId' + selectedRoom).classList.remove('active');
    }
    document.getElementById('roomId' + id).classList.add('active');
}

function dateStringtoDate(stringDateTime) {
    var dateString = (stringDateTime + "").split(/-|\s|:/);
    return new Date(dateString[0], dateString[1] - 1, dateString[2], dateString[3], dateString[4], dateString[5]);
}

function clearUnseenMessageNotify(id) {
    $('#roomNewMessageId' + id).text("");
}

/*
    ***Function to get related User List***
    related User : Who get in the conversation
*/
function getRelatedUserList() {
    $.get("http://localhost:8888/getRelatedUsers?token=" + token, function (data) {
        console.log(data);
        let users = data;
        for (let i = 0; i < users.length; i++) {
            RealetedUserMap.set(users[i].userId, users[i]);
        }
    }).fail(function (error) {
        console.log("Error in getRelatedUserList()")
    })

}


setAllUserProfile();

function featchAllRoom() {
    $.get("http://localhost:8888/fetchAllRoom?token=" + token, function (data) {
        console.log(data);
        let rooms = data;
        let roomTemplateHtml = "";

        for (let i = 0; i < rooms.length; i++) {
            // Date
            let roomChatDateTime = " ";
            var dateString = (rooms[i].latestUpdateDate + "").split(/-|\s|:/);
            var date = new Date(dateString[0], dateString[1] - 1, dateString[2], dateString[3], dateString[4], dateString[5]); // decrease month value by 1
            var todayDate = new Date();
            if (date != null) {
                if (date.getDate() == todayDate.getDate()) {
                    roomChatDateTime = addZeroToAnyThing(date.getHours()) + ':' + addZeroToAnyThing(date.getMinutes());
                } else {
                    roomChatDateTime = addZeroToAnyThing(date.getDate()) + " " + shortmonthInYear[date.getMonth()];
                }
            }

            // Room Chat Image
            let roomChatImage = "https://user-images.githubusercontent.com/35243461/168796877-f6c8819a-5d6e-4b2a-bd56-04963639239b.jpg"
            if (rooms[i].roomImage != null) {
                roomChatImage = rooms[i].roomImage;
            }

            let roomName = rooms[i].name;
            if (!rooms[i].roomType) {
                if (rooms[i].user1 == userId) {
                    roomName = RealetedUserMap.get(rooms[i].user2).username;
                    roomChatImage = RealetedUserMap.get(rooms[i].user2).avatar;
                } else {
                    roomName = RealetedUserMap.get(rooms[i].user1).username;
                    roomChatImage = RealetedUserMap.get(rooms[i].user1).avatar;
                }
            }


            roomTemplateHtml = roomTemplateHtml +
                '<div class="chat-item d-flex pl-3 pr-0 pt-3 pb-3" id="roomId' + rooms[i].room_id + '"  onclick=changeSelectedRoom(' + rooms[i].room_id + ')> \n' +
                '<div class="w-100"> \n' +
                '<div class="d-flex pl-0">\n' +
                '<img class="rounded-circle shadow avatar-sm mr-3" src="' + roomChatImage + '" style="width: 40px; height: 40px; object-fit: cover;" /> \n' +
                '<div>\n' +
                '<p class="margin-auto fw-400 text-dark-75">' + roomName + '</p>\n' +
                '<div class="d-flex flex-row mt-1">\n' +
                '<span>\n' +
                '<div class="svg15 double-check"></div>\n' +
                '</span>\n' +
                '<span class="message-shortcut margin-auto fw-400 fs-13 ml-1 mr-4">Hey Quan, If you are free now we can meet tonight ?</span>\n' +
                '</div>\n' +
                '</div>\n' +
                '</div>\n' +
                '</div>\n' +
                '<div class="flex-shrink-0 margin-auto pl-2 pr-3">\n' +
                '<div class="d-flex flex-column">\n' +
                '<p id="roomDateTime' + rooms[i].room_id + '"  class="text-muted text-right fs-13 mb-2">' + roomChatDateTime + '</p>\n' +
                '<span id="roomNewMessageId' + rooms[i].room_id + '" class="round badge badge-light-success margin-auto"></span>\n' +
                '</div>\n' +
                '</div>\n' +
                '</div>\n';

        }
        $('#roomList').html('');
        $('#roomList').html(roomTemplateHtml);

    }).fail(function (error) {
        console.log("Error in featch rooms " + error);
    })
}

setTimeout(function () {
    getRelatedUserList();
    setTimeout(function () {
        featchAllRoom();
    }, 100)
}, 100);

function getRoomInformation(roomId) {
    $.get("http://localhost:8888/getRoomInfo?token=" + token + "&roomId=" + roomId, function (data) {
        console.log(data);
        // Room image
        var chatRoomImage = "";
        if (data.roomType) {
            chatRoomImage = data.roomImage;
        } else {
            if (data.user1 == userId) {
                chatRoomImage = RealetedUserMap.get(data.user2).avatar;
            } else {
                chatRoomImage = RealetedUserMap.get(data.user1).avatar;
            }
        }
        document.getElementById("roomImageChatBar").src = chatRoomImage;


        // Room name
        var roomName = data.name;
        if (!data.roomType) {
            if (data.user1 == userId) {
                roomName = RealetedUserMap.get(data.user2).username;
            } else {
                roomName = RealetedUserMap.get(data.user1).username;
            }
        }
        $('#roomNameChatBar').text(roomName);


        // Room Lastes update
        var dateTime = "";
        var date = dateStringtoDate(data.latestUpdateDate);
        var todayDate = new Date();
        if (date.getDate() == todayDate.getDate()) {
            dateTime = "today at " + addZeroToAnyThing(date.getHours()) + ':' + addZeroToAnyThing(date.getMinutes());
        } else {
            dateTime = addZeroToAnyThing(date.getDate()) + " " + shortmonthInYear[date.getMonth()] + " " + addZeroToAnyThing(date.getHours()) + ':' + addZeroToAnyThing(date.getMinutes());
        }
        $('#roomLastestUpdateChatBar').html('<i class="la la-clock mr-1"></i>last seen ' + dateTime);

    }).fail(function (error) {
        console.log("Error form getRoomInformation() with roomId " + roomId + " " + error);
    })
}

function getUserRoomInfomation(roomId) {
    console.log("getUserRoomInfomation");
    $.get("http://localhost:8888/getUserRoomInfo?token=" + token + "&roomId=" + roomId, function (data) {
        var userRooms = data;
        // userRoom
        console.log(userRooms);
        for (let i = 0; i < userRooms.length; i++) {
            userRoom.set(userRooms[i].userId, userRooms[i]);
        }
    }).fail(function (error) {
        console.log("Error form getRoomInformation() with roomId " + roomId + " " + error);
        console.log(error);
    })
}

/*
    Change Last Update()
    This class will change the date time lastupdate in any room in client by id "roomDateTime" + roomId
*/

function changeSelectedRoom(roomId) {
    if (selectedRoom != roomId) {
        isExitsNewPage = true;
        page = 1;
        console.log("changeSelectedRoom " + roomId);

        //Clear the notification (notifi when new message arrived but the user in other room) clear that
        clearUnseenMessageNotify(roomId);
        // Remove the old one active class
        changeActive(roomId);
        dateToGenerateChat = formatDate(new Date());
        // Generate first page chat
        chatInSelectedRoom = "";
        userRoom.clear();
        let p = new Promise((resolve, reject) => {
            getUserRoomInfomation(roomId);
            setTimeout(() => {
                resolve("Success");
            }, 20);
        })
        p.then(() => {
            getRoomInformation(roomId);
        }).then(() => {
            getChatMessageByPage(roomId, 1, dateToGenerateChat);
        }).finally(() => {
            console.log("Done");
        })
        // setTimeout(function() {
        //     getChatMessageByPage(roomId, 1, dateToGenerateChat);
        //         setTimeout(function() {
        //             getRoomInformation(roomId);
        //         }, 100)
        // }, 100);
        //Promise.all([getUserRoomInfomation(roomId), getChatMessageByPage(roomId, 1, dateToGenerateChat), getRoomInformation(roomId)])
        selectedRoom = roomId;
    }
}

function changeLastUpdate(date, roomId) {
    var roomChatDateTime = addZeroToAnyThing(date.getHours()) + ':' + addZeroToAnyThing(date.getMinutes());
    $('#roomDateTime' + roomId).text(roomChatDateTime);
}


function generateNewMessage(data) {

    let userAvatar = "https://user-images.githubusercontent.com/35243461/168796877-f6c8819a-5d6e-4b2a-bd56-04963639239b.jpg";
    if (RealetedUserMap.get(data.userId).avatar != null) {
        userAvatar = RealetedUserMap.get(data.userId).avatar;
    }

    let userChatName = "Unknow";
    if (userRoom.get(data.userId).aliasName != null) {
        userChatName = userRoom.get(data.userId).aliasName;
    } else {
        if (RealetedUserMap.get(data.userId).username != null) {
            userChatName = RealetedUserMap.get(data.userId).username;
        }
    }
    var dateString = (data.createdDate + "").split(/-|\s|:/);
    var date = new Date(dateString[0], dateString[1] - 1, dateString[2], dateString[3], dateString[4], dateString[5]);
    let roomChatDateTime = addZeroToAnyThing(date.getHours()) + ':' + addZeroToAnyThing(date.getMinutes());
    htmlMessageTemplate = '<div>\n' +
        '<div class="ml-3 mb-0" >\n' +
        '<p style="margin-bottom: 0px; font-size: small; ">' + userChatName + '</p>\n' +
        '</div>\n' +
        '<div class="left-chat-message fs-13 mb-2">\n' +
        '<div class="row">\n' +
        '<div class="col-xl-2 col-sm-3 ">\n' +
        '<img src="' + userAvatar + '" class="rounded-circle mr-1 mb-sm-5 " alt="Chris Wood" width="40" height="40">\n' +
        '</div>\n' +
        '<div class="col-xl-10 col-sm-9">\n' +
        '<p class="mb-0 mr-3 mr-2  pr-4">' + data.message + '</p>\n' +
        '<div class="message-options mt-3">\n' +
        '<div class="message-time">' + roomChatDateTime + '</div>\n' +
        '<div class="message-arrow"><i class="text-muted la la-angle-down fs-17"></i></div>\n' +
        '</div>\n' +
        '</div>\n' +
        '</div>\n' +
        '</div>\n' +
        '</div>\n';

    chatInSelectedRoom += htmlMessageTemplate;
    $('#chatBoxMessage').html(chatInSelectedRoom);

}


function connectToChat(userId) {
    console.log("connecting to chat... ")
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userId, function (response) {
            let data = JSON.parse(response.body);
            console.log(data);
            if (selectedRoom === data.roomId) {
                generateNewMessage(data);
                var dateString = (data.createdDate + "").split(/-|\s|:/);
                var date = new Date(dateString[0], dateString[1] - 1, dateString[2], dateString[3], dateString[4], dateString[5]);
                changeLastUpdate(date, data.roomId)
            } else {
                changeLastUpdate(dateStringtoDate(data.createdDate), data.roomId)
                var intMessage = 1;
                if (!Object.is(parseInt($('#roomNewMessageId' + data.roomId).text()), NaN)) {
                    intMessage = parseInt($('#roomNewMessageId' + data.roomId).text()) + 1;
                }
                $('#roomNewMessageId' + data.roomId).text(intMessage.toString());

            }
        });
    });
}

connectToChat(userId);

function generateOwnMessage(messageText, date) {
    let roomChatDateTime = addZeroToAnyThing(date.getHours()) + ':' + addZeroToAnyThing(date.getMinutes());
    let htmlMessageTemplate = '<div class="d-flex flex-row-reverse mb-2">\n' +
        '<div class="right-chat-message fs-13 mb-2">\n' +
        '<div class="d-flex flex-row mb-3">\n' +
        '<div class="pr-2">' + messageText + '</div>\n' +
        '<div class="pr-4"></div>\n' +
        '</div>\n' +
        '<div class="message-options dark">\n' +
        '<div class="message-time">\n' +
        '<div class="d-flex flex-row">\n' +
        '<div class="mr-2">' + roomChatDateTime + '</div>\n' +
        '<div class="svg15 double-check"></div>\n' +
        '</div>\n' +
        '</div>\n' +
        '<div class="message-arrow"><i class="text-muted la la-angle-down fs-17"></i></div>\n' +
        '</div>\n' +
        '</div>\n' +
        '</div>\n';

    chatInSelectedRoom += htmlMessageTemplate;
    $('#chatBoxMessage').html(chatInSelectedRoom);
    scrollToBottum();
}

function sendMessage() {

    if (selectedRoom != -1) {
        if (document.getElementById("message").value.length == 0 || document.getElementById("message").value == null) {
            console.log("Message null");
            return;
        }
        let messageText = document.getElementById("message").value;
        let selectedRoomId = selectedRoom;
        var date = new Date();
        generateOwnMessage(messageText, date)
        changeLastUpdate(date, selectedRoomId);
        document.getElementById("message").value = "";
        stompClient.send("/app/chat/" + selectedRoomId, {}, JSON.stringify({
            messageId: -1,
            message: messageText,
            createdDate: formatDate(date),
            roomId: selectedRoomId,
            userId: userId
        }));
    }
}

function getChatMessageByPage(roomId, page, date) {
    $.get("http://localhost:8888/getMessageByPage?token=" + token + "&roomId=" + roomId + "&page=" + page + "&date=" + date, function (data) {
        console.log(data);
        let messages = data;
        var htmlMessageTemplate = '';
        if (page == 1 && data.length == 0) {
            $('#chatBoxMessage').html(chatInSelectedRoom);
            return;
        }
        if (data.length == 0) {
            isExitsNewPage = false;
            let element = document.getElementById("loadingElement");
            element.remove();
            return;
        }
        for (const [key, value] of userRoom.entries()) {
            console.log(key, value);
        }

        for (let i = 0; i < messages.length; i++) {

            let roomChatDateTime = " ";
            var dateString = (messages[i].createdDate + "").split(/-|\s|:/);
            var date = new Date(dateString[0], dateString[1] - 1, dateString[2], dateString[3], dateString[4], dateString[5]); // decrease month value by 1
            var todayDate = new Date();
            if (date != null) {
                if (date.getDate() == todayDate.getDate()) {
                    roomChatDateTime = addZeroToAnyThing(date.getHours()) + ':' + addZeroToAnyThing(date.getMinutes());
                } else {
                    roomChatDateTime = addZeroToAnyThing(date.getDate()) + " " + shortmonthInYear[date.getMonth()];
                }
            }


            if (messages[i].userId == userId) {

                htmlMessageTemplate = htmlMessageTemplate +
                    '<div class="d-flex flex-row-reverse mb-2">\n' +
                    '<div class="right-chat-message fs-13 mb-2">\n' +
                    '<div class="d-flex flex-row mb-3">\n' +
                    '<div class="pr-2">' + messages[i].message + '</div>\n' +
                    '<div class="pr-4"></div>\n' +
                    '</div>\n' +
                    '<div class="message-options dark">\n' +
                    '<div class="message-time">\n' +
                    '<div class="d-flex flex-row">\n' +
                    '<div class="mr-2">' + roomChatDateTime + '</div>\n' +
                    '<div class="svg15 double-check"></div>\n' +
                    '</div>\n' +
                    '</div>\n' +
                    '<div class="message-arrow"><i class="text-muted la la-angle-down fs-17"></i></div>\n' +
                    '</div>\n' +
                    '</div>\n' +
                    '</div>\n';
            } else {

                let userAvatar = "https://user-images.githubusercontent.com/35243461/168796877-f6c8819a-5d6e-4b2a-bd56-04963639239b.jpg";
                if (RealetedUserMap.get(messages[i].userId).avatar != null) {
                    userAvatar = RealetedUserMap.get(messages[i].userId).avatar;
                }
                let userChatName = "Unknow";
                //userRoom
                if (userRoom.get(messages[i].userId).aliasName != null) {
                    userChatName = userRoom.get(messages[i].userId).aliasName;
                } else {
                    if (RealetedUserMap.get(messages[i].userId).username != null) {
                        userChatName = RealetedUserMap.get(messages[i].userId).username;
                    }
                }
                htmlMessageTemplate = htmlMessageTemplate +
                    '<div>\n' +
                    '<div class="ml-3 mb-0" >\n' +
                    '<p style="margin-bottom: 0px; font-size: small; ">' + userChatName + '</p>\n' +
                    '</div>\n' +
                    '<div class="left-chat-message fs-13 mb-2">\n' +
                    '<div class="row">\n' +
                    '<div class="col-xl-2 col-sm-3 ">\n' +
                    '<img src="' + userAvatar + '" class="rounded-circle mr-1 mb-sm-5 " alt="Chris Wood" width="40" height="40">\n' +
                    '</div>\n' +
                    '<div class="col-xl-10 col-sm-9">\n' +
                    '<p class="mb-0 mr-3 mr-2  pr-4">' + messages[i].message + '</p>\n' +
                    '<div class="message-options mt-3">\n' +
                    '<div class="message-time">' + roomChatDateTime + '</div>\n' +
                    '<div class="message-arrow"><i class="text-muted la la-angle-down fs-17"></i></div>\n' +
                    '</div>\n' +
                    '</div>\n' +
                    '</div>\n' +
                    '</div>\n' +
                    '</div>\n';
            }
        }

        chatInSelectedRoom = htmlMessageTemplate + chatInSelectedRoom;
        generateMessage();
    }).fail(function (error) {
        console.log("Error in generateChatMessage()" + error);
    })

}

function scrollingDetect() {
    var element = document.getElementById("chatBoxMessage");
    let x = element.scrollLeft;
    let y = element.scrollTop;
    let maxY = element.scrollHeight;
    if (y == 0 && selectedRoom != -1) {
        if (isExitsNewPage) {
            lastMaxHeight = element.scrollHeight;
            page++;
            $("#chatBoxMessage").prepend(loadingHtml);
            getChatMessageByPage(selectedRoom, page, dateToGenerateChat);
        }

    }
    console.log(page);
    console.log("Horizontally: " + x.toFixed() + " Vertically: " + y.toFixed() + " Position: " + maxY);
}

$(document).keypress(function (e) {
    if (e.which == 13) {
        $("#sendMessage").click();
    }
});


document.getElementById('searchBar').addEventListener('click', searchActivated);
// function searchInactivated() {
//     document.getElementById('searchBarIcon').classList.remove('la-times');
//     document.getElementById('searchBarIcon').classList.add('la-search');
//     document.getElementById('archivedMessage').classList.remove('display-none-for-div');
//     document.getElementById('searchList').classList.add('display-none-for-div');
//     document.getElementById('roomList').classList.remove('display-none-for-div');
//     // document.getElementById('searchBarIcon').removeEventListener("click", searchInactivated);
//     // document.getElementById('searchBar').addEventListener('click', searchActivated);
// }

function searchActivated() {
    console.log("doi den");
    document.getElementById('searchBarIcon').classList.remove('la-search');
    document.getElementById('searchBarIcon').classList.add('la-times');
    document.getElementById('archivedMessage').classList.add('display-none-for-div');
    document.getElementById('searchList').classList.remove('display-none-for-div');
    document.getElementById('roomList').classList.add('display-none-for-div');
    // document.getElementById('searchBarIcon').addEventListener("click", searchInactivated);
    // document.getElementById('searchBar').removeEventListener('click', searchActivated);
}

    

    