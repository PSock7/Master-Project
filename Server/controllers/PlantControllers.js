const Plant = require('../models/Plant');
const User = require('../models/User');
const asyncHandler = require('express-async-handler');

// @desc Get all plants
// @route GET /plants
// @access Private
const getAllPlants = asyncHandler(async (req, res) => {
  const plants = await Plant.find().lean();

  if (!plants?.length) {
    return res.status(400).json({ message: 'No plants found' });
  }

  const plantsWithUser = await Promise.all(
    plants.map(async (plant) => {
      const user = await User.findById(plant.user).lean().exec();
      return { ...plant, username: user.username };
    })
  );

  res.json(plantsWithUser);
});

// @desc Create a new plant
// @route POST /plants
// @access Private
const createNewPlant = asyncHandler(async (req, res) => {
  const { user, name, description, seuil, image } = req.body;

  if (!user || !name || !description || !seuil || !image) {
    return res.status(400).json({ message: 'All fields are required' });
  }

  const duplicate = await Plant.findOne({ name }).lean().exec();

  if (duplicate) {
    return res.status(409).json({ message: 'Duplicate plant name' });
  }

  const plant = await Plant.create({ user, name, description, seuil, image });

  if (plant) {
    return res.status(201).json({ message: 'New plant created' });
  } else {
    return res.status(400).json({ message: 'Invalid plant data received' });
  }
});

// @desc Update a plant
// @route PATCH /plants/:id
// @access Private
const updatePlant = asyncHandler(async (req, res) => {
  const { id, user, name, description, seuil, image } = req.body;

  if (!id || !user || !name || !description || !seuil || !image) {
    return res.status(400).json({ message: 'All fields are required' });
  }

  const plant = await Plant.findById(id).exec();

  if (!plant) {
    return res.status(400).json({ message: 'Plant not found' });
  }

  const duplicate = await Plant.findOne({ name }).lean().exec();

  if (duplicate && duplicate._id.toString() !== id) {
    return res.status(409).json({ message: 'Duplicate plant name' });
  }

  plant.user = user;
  plant.name = name;
  plant.description = description;
  plant.seuil = seuil;
  plant.image = image;

  const updatedPlant = await plant.save();

  res.json(`'${updatedPlant.name}' updated`);
});

// @desc Delete a plant
// @route DELETE /plants/:id
// @access Private
const deletePlant = asyncHandler(async (req, res) => {
  const { id } = req.body;

  if (!id) {
    return res.status(400).json({ message: 'Plant ID required' });
  }

  const plant = await Plant.findById(id).exec();

  if (!plant) {
    return res.status(400).json({ message: 'Plant not found' });
  }

  const result = await plant.deleteOne();

  const reply = `Plant '${result.name}' with ID ${result._id} deleted`;

  res.json(reply);
});

module.exports = {
  getAllPlants,
  createNewPlant,
  updatePlant,
  deletePlant,
};
