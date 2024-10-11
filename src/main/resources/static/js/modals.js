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