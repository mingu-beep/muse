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