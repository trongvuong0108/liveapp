import { io } from "socket.io-client";

const getEle = (id) => document.getElementById(id);
const socket = io("http://localhost:2224");
const username = "admin2";
const productId = null;
const role = false;

socket.emit("get-live", username, role, null, productId);
socket.on("receive-live", (urlServer, productItem, liver) => {
  const temp = urlServer.split("=");
  const urlLive = `https://www.youtube.com/embed/${temp[1]}`;
  getEle("liver").innerText = liver;
  getEle("video").innerHTML = `<iframe
        width="560"
        height="315"
        src=${urlLive}
        title="YouTube video player"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowfullscreen
      ></iframe>`;
});
socket.on("product-number", (number) => {
  getEle("number").innerText = number;
});
socket.on("get-viewer", (number) => {
  getEle("viewer").innerText = number;
});
socket.on("enable-buy-client", (bool) => {
  let buyable = bool ? "đang mở bán" : "đang đóng bán";
  getEle("buyable").innerHTML = buyable;
});
socket.on("admin-close-live", (closed) => {
    console.log(closed)
    alert("live stream kết thúc")
})
