const { StatusCodes } = require("http-status-codes");
function validateRequest(schema) {
  return function (req, res, next) {
    const { error } = schema.validate(req.body);
    if (error) {
      return res
        .status(StatusCodes.BAD_REQUEST)
        .json({ error: error.details[0].message });
    }
    next();
  };
}

module.exports = validateRequest;
