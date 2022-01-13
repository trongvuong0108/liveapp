const express = require("express");
const app = express();
const db = require("./models");
const path = require("path");
const http = require("http");
const rootRouter = require("./routers/root-router");
const port = process.env.PORT || 2222;
const socketio = require("socket.io");

const publicPathDirectory = path.join(__dirname, "./");
app.use(express.static(publicPathDirectory));

app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use("/router", rootRouter);

db.sequelize.sync().then(() => {
  app.listen(port, () => {
    console.log("Server is running on port " + port);
  });
});

  