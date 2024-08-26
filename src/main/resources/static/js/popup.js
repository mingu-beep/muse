function sendToParent(count) {

  let artist = $(`#artist-${count}`).html();
  let track = $(`#track-${count}`).html();

  console.log(`#artist-${count}`);
  console.log(`#track-${count}`);
  console.log(artist);
  console.log(track);

  opener.document.getElementById('title').value = track;
  opener.document.getElementById('artist').value = artist;

  self.close();
}

