const mongoose = require("mongoose");


const plantSchema = new mongoose.Schema(
  {
    user: {
      type: mongoose.Schema.Types.ObjectId,
      required: true,
      ref: 'User'
  },
    name: {
      type: String,
      required: true,
      unique: true
    },
    description: {
      type: String,
      required: true,
    },
    seuil: {
      type: Number,
      required: true,
      min: 0,
      max : 100,
    },
    image:{
        type:String,
        required: true
    }
  },
  { timestamps: true }
);
const Plant = mongoose.model("Plant", plantSchema);

module.exports = Plant;