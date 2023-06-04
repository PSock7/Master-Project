const express = require("express");
const router = express.Router();
const Joi = require("joi");
const validateRequest = require("../middleware/validation");
const {
  addPlant,
  updatePlant,
  deletePlant,
} = require("../controllers/PlantControllers");

// Schéma de validation pour l'ajout d'une plante
const addPlantSchema = Joi.object({
  name: Joi.string().required(),
  description: Joi.string().required(),
  seuil: Joi.number().required(),
});

// Schéma de validation pour la modification d'une plante
const updatePlantSchema = Joi.object({
  name: Joi.string(),
  description: Joi.string(),
  seuil: Joi.number(),
});

// Route pour l'ajout d'une plante
router.post("/plants", validateRequest(addPlantSchema), addPlant);

// Route pour la modification d'une plante
router.put("/plants/:id", validateRequest(updatePlantSchema), updatePlant);

// Route pour la suppression d'une plante
router.delete("/plants/:id", deletePlant);

module.exports = router;
