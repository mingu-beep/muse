// (1) 스토리 이미지 업로드를 위한 사진 선택 로직
function imageChoose(obj) {
	let f = obj.files[0];

	if (!f.type.match("image.*")) {
		alert("이미지를 등록해야 합니다.");
		return;
	}

	let reader = new FileReader();
	reader.onload = (e) => {
		$("#imageUploadPreview").attr("src", e.target.result);
	}
	reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
}

function apiTrack() {
  const title = document.getElementById("title").value;
  console.log(title)
  var url = `/api/last?track=${title}&page=1`;
  var winWidth = 600;
  var winHeight = 500;
  var popupOption = "width=" + winWidth + ", height=" + winHeight;
  window.open(url, "Title 기반 검색", popupOption);
}