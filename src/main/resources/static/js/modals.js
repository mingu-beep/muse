function saveMood() {

    const label = $(`#labelInput`).val();
    const script = $(`#scriptInput`).val()

    let dto = {
        label: label,
        script: script
    };

    $.ajax({
        type:'POST',
        url:'/admin/mood',
        data:JSON.stringify(dto),
        contentType:"application/json; charset=utf-8",
        dataType: "json"
    }).done(res => {
        console.log("ok : " + res);

        $(`#addMoodModal`).modal('hide');

        let moodList = $(`#mood_table_body`)

        let newMood = `
            <tr>
                <td>${res.id}</td>
                <td>${res.label}Label</td>
                <td>${res.script}</td>
            </tr>
        `

        moodList.append(newMood);

    }).fail(err => {
        console.log("fail");
    });
}

function checkDelete(musicId) {
    if(confirm("정말 삭제하시겠습니까?") == true) {
        deleteMusic(musicId);
    } else {
        return false;
    }
}

function deleteMusic(musicId) {

    console.log("delete Music id : " + musicId);
    $.ajax({
        type:'DELETE',
        url:`/musics/${musicId}`
    }).done(res => {

        $(`#musicModal${musicId}`).modal('hide');
        $(`#musicCard${musicId}`).remove();

        location.replace(location.href);

    }).fail(err => {
        alert("삭제 실패");
    });
}

function updateMusic(musicId) {
    console.log("update Music id : " + musicId);
    location.href = `/musics/${musicId}`
}

function updateProfile(userId) {

//    let profileUpdateForm = $("#profileUpdateForm")[0];
    let profileUpdateForm = document.getElementById('profileUpdateForm');

    // Ajax로 form 데이터를 전송하기 위해 FormData 객체에 담기
    let formData = new FormData(profileUpdateForm);

    console.log(formData);

    $.ajax({
        type: "put",
        url: `/user/${userId}`,
        data : formData,
        contentType: true, // true 일 경우 x-www-form-urlencoded
        processData : false,
        enctype : "multipart/form-data",
        dataType : "json"
    }).done(res => {
        console.log("성공", res)
    }).fail(error => {
        console.log("오류", error);
    });

}

function toggleLike(musicId) {
	let likeIcon = $(`#likesInModal${musicId}`);

	if (likeIcon.hasClass("fa-solid")) { // unlikes

	    $.ajax({
	        type: "delete",
	        url: `/api/musics/${musicId}/likes`
	    }).done( res => {
	        console.log("좋아요 삭제 성공", res);
	        likeIcon.removeClass("fa-solid");
            likeIcon.addClass("fa-regular");
            likeIcon.css('color', '')
	    }).fail( err => {
	        console.log("좋아요 삭제 실패", err);
	    })

	} else { // Likes

	    $.ajax({
            type: "post",
            url: `/api/musics/${musicId}/likes`
        }).done( res => {
            console.log("좋아요 추가 성공", res);
            likeIcon.removeClass("fa-regular");
            likeIcon.addClass("fa-solid");
            likeIcon.css('color', 'red');
        }).fail( err => {
            console.log("좋아요 추가 실패", err);
        })

	}

}

function saveComment(musicId) {

    let content = $(`#commentInput${musicId}`);

    let data = {
        musicId: musicId,
        content: content.val()
    };

    $.ajax({
        type: "post",
        url: "/api/comments",
        data: JSON.stringify(data),
        contentType:"application/json; charset=utf-8",
        dataType: "json"
    }).done(res => {
        console.log("res : ", res);
        let commentList = $(`#comment_item_list${musicId}`)

        // 현재 날짜와 시간을 가져오기
        const currentDate = new Date();

        // 날짜와 시간을 문자열로 포맷팅
        const formattedDate = `${currentDate.getFullYear()}-${currentDate.getMonth() + 1}-${currentDate.getDate()} ${currentDate.getHours()}:${currentDate.getMinutes()}:${currentDate.getSeconds()}`;

        let newComment = `
            <div th:id="comment_item${res.id}">
                <div th:id="comment_item_header${res.id}" class="d-flex justify-content-between">
                    <p>${res.username}</p>
                    <p>${formattedDate}</p>
                </div>

                <p th:id="comment_item_body_content${res.id}" class="p-3"
                   >${res.content}</p>

                <div class="d-flex justify-content-end">
                    <a class="p-2" th:onclick="showUpdateForm(${res.musicId}, ${res.id})"><i class="fa-solid fa-pen"></i></a>
                    <a class="p-2" th:onclick="deleteComment(${res.id})"><i class="fa-solid fa-x"></i></a>
                </div>
            </div>
        `

        commentList.append(newComment);
        content.val("");

    }).fail(err => {
        console.log("err : " + err);
    });
}

function deleteComment(commentId) {

    $.ajax({
        type: "delete",
        url: `/api/comments/${commentId}`
    }).done(res => {
        console.log("res : ", res);
        $(`#comment_item${commentId}`).remove();

    }).fail(err => {
        console.log("err : ", err);
    })
}

function showUpdateForm(musicId, commentId) {

    let updateInputGroup =
    `
    <div class="input-group mb-3" id="updateFormGroup${commentId}">
          <input id="updateInput${commentId}" type="text" class="form-control" aria-describedby="update-form">
          <button class="btn btn-outline-secondary" type="button" id="update-form" onclick="updateComment(${musicId}, ${commentId})">Update</button>
          <div class="d-flex justify-content-end">
                <a class="p-2" onclick="removeUpdateForm(${commentId})"><i class="fa-solid fa-x"></i></a>
          </div>
    </div>
    `;

    let commentItem = $(`#comment_item${commentId}`)
    commentItem.append(updateInputGroup);

}

function removeUpdateForm(commentId) {
    $(`#updateFormGroup${commentId}`).remove();
}

function updateComment(musicId, commentId) {

    let content = $(`#updateInput${commentId}`);

    let data = {
        musicId: musicId,
        content: content.val()
    };

    $.ajax({
        type: "put",
        url: `/api/comments/${commentId}`,
        data: JSON.stringify(data),
        contentType:"application/json; charset=utf-8",
        dataType: "json"
    }).done(res => {
        console.log("res : ", res);
        $(`#comment_item_body_content${commentId}`).text(res.content);
        $(`#updateFormGroup${commentId}`).remove();

    }).fail(err => {
        console.log("err : " + err);
    });
}