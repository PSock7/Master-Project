const express = require("express");
const router = express.Router();
const authController = require("../controllers/AuthControllers.js");

router.use("/auth", authController);

module.exports = router;
