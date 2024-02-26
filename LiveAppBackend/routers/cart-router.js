const express = require("express");
const cartController = require("../controllers/cart-controller");

const cartRouter = express.Router();

cartRouter.post("/getList", cartController.list);

cartRouter.put("/", cartController.update);

cartRouter.post("/", cartController.create);

cartRouter.post("/del", cartController.delete);

cartRouter.delete("/multi", cartController.multiDelete);

cartRouter.put("/checkexits", cartController.checkExits);
module.exports = cartRouter;
