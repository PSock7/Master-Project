const asyncHandler = require('express-async-handler');
const Plant = require('../models/Plant');

// @desc Ajouter une plante
// @route POST /plants
// @access Private
const addPlant = asyncHandler(async (req, res) => {
  const { name, description, seuil, image } = req.body;
  const userId = req.user._id; // ID de l'utilisateur connecté

  const plant = await Plant.create({ user: userId, name, description, seuil, image });

  res.status(201).json(plant);
});

// @desc Modifier une plante
// @route PUT /plants/:id
// @access Private
const updatePlant = asyncHandler(async (req, res) => {
  const { name, description, seuil, image } = req.body;
  const plantId = req.params.id;
  const userId = req.user._id; // ID de l'utilisateur connecté

  const plant = await Plant.findOne({ _id: plantId, user: userId });

  if (!plant) {
    return res.status(404).json({ message: 'Plante non trouvée' });
  }

  plant.name = name;
  plant.description = description;
  plant.seuil = seuil;
  plant.image = image;

  const updatedPlant = await plant.save();

  res.json(updatedPlant);
});

// @desc Supprimer une plante
// @route DELETE /plants/:id
// @access Private
const deletePlant = asyncHandler(async (req, res) => {
  const plantId = req.params.id;
  const userId = req.user._id; // ID de l'utilisateur connecté

  const plant = await Plant.findOne({ _id: plantId, user: userId });

  if (!plant) {
    return res.status(404).json({ message: 'Plante non trouvée' });
  }

  await plant.remove();

  res.json({ message: 'Plante supprimée' });
});

module.exports = {
  addPlant,
  updatePlant,
  deletePlant,
};
