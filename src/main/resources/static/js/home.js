let page = 0;

function storyLoad() {
    $.ajax({
        type:"get",
        url:`/api/musics?page=${page}`,
        dataType: "json"
    }).done(res => {

        console.log("done");
        res.forEach((music) => {
            console.log("music : ", music);
        });
    }).fail(err => {
        console.log("fail", err);
    });
}

storyLoad();