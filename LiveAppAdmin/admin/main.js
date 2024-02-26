import { io } from "socket.io-client";

const getEle = (id) => document.getElementById(id);
const socket = io("http://localhost:2224");
const username = "admin";
const role = true;
let url;
getEle("open-buy").addEventListener("click", () => {
  const text = getEle("open-buy").innerText;
  if (text === "mở bán") {
    socket.emit("enable-buy", true);
    socket.on("enable-buy-client", (bool) => {
      let buyable = bool ? "đang mở bán" : "đang đóng bán";
      getEle("buyable").innerHTML = buyable;
    });
    getEle("open-buy").innerText = "đóng bán";
  } else {
    socket.emit("enable-buy", false);
    socket.on("enable-buy-client", (bool) => {
      let buyable = bool ? "đang mở bán" : "đang đóng bán";
      getEle("buyable").innerHTML = buyable;
    });
    getEle("open-buy").innerText = "mở bán";
  }
});
getEle("open-live").addEventListener("click", () => {
  const text = getEle("open-live").innerText;
  if (text === "mở stream") {
    const productId = getEle("productId").value;
    url = getEle("url-live").value;
    const temp = url.split("=");
    const urlLive = `https://www.youtube.com/embed/${temp[1]}`;
    socket.emit("get-live", username, role, url, productId);
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
    getEle("open-live").innerText = "đóng stream";
    getEle("video").innerHTML = `<iframe
        width="560"
        height="315"
        src=${urlLive}
        title="YouTube video player"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowfullscreen
      ></iframe>`;
  } else {
    getEle("video").innerHTML = "";
    getEle("number").innerHTML = "";
    getEle("viewer").innerHTML = "";
    getEle("buyable").innerHTML = "";
    getEle("open-live").innerText = "mở stream";
    socket.emit("close-live");
  }
});

document.getElementById("change-product").addEventListener("click", () => {
  console.log(123)
  const productId = getEle("productId").value;
  socket.emit("change-product", productId);
  socket.on("product-number", (number) => {
    getEle("number").innerText = number;
  });
});
