const express = require("express");
const router = express.Router();
const Joi = require("joi");
const validateRequest = require("../middleware/validation");
const { register, login } = require("../controllers/AuthControllers");

const registerSchema = Joi.object({
  name: Joi.string().required(),
  email: Joi.string().email().required(),
  password: Joi.string().min(3).required(),
});

const loginSchema = Joi.object({
  email: Joi.string().email().required(),
  password: Joi.string().min(3).required(),
});

router.route("/register").post(validateRequest(registerSchema), register);

router.route("/login").post(validateRequest(loginSchema), login);


module.exports = router;
