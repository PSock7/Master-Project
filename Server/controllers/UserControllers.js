const { StatusCodes } = require("http-status-codes");
const User = require('../models/User');
const Plant = require('../models/Plant');
const asyncHandler = require('express-async-handler');
const bcrypt = require('bcrypt');

// Fonction pour obtenir tous les utilisateurs
const getAllUsers = asyncHandler(async (req, res) => {
  const users = await User.find().select('-password').lean();

  if (!users?.length) {
    return res.status(StatusCodes.BAD_REQUEST).json({ message: 'No users found' });
  }

  res.json(users);
});

// Fonction pour créer un nouvel utilisateur
const createNewUser = asyncHandler(async (req, res) => {
  const { name, email, password } = req.body;

  if (!name || !email || !password) {
    return res.status(StatusCodes.BAD_REQUEST).json({ message: 'All fields are required' });
  }

  const duplicateEmail = await User.findOne({ email }).lean().exec();

  if (duplicateEmail) {
    return res.status(StatusCodes.CONFLICT).json({ message: 'Duplicate email' });
  }

  const hashedPassword = await bcrypt.hash(password, 10);

  const newUser = await User.create({ name, email, password: hashedPassword });

  if (newUser) {
    res.status(StatusCodes.CREATED).json({ message: `New user ${name} created` });
  } else {
    res.status(StatusCodes.BAD_REQUEST).json({ message: 'Invalid user data received' });
  }
});

// Fonction pour mettre à jour un utilisateur
const updateUser = asyncHandler(async (req, res) => {
  const { id, name, email, password } = req.body;

  if (!id || !name || !email) {
    return res.status(StatusCodes.BAD_REQUEST).json({ message: 'All fields except password are required' });
  }

  const user = await User.findById(id).exec();

  if (!user) {
    return res.status(StatusCodes.BAD_REQUEST).json({ message: 'User not found' });
  }

  const duplicateEmail = await User.findOne({ email }).lean().exec();

  if (duplicateEmail && duplicateEmail._id.toString() !== id) {
    return res.status(StatusCodes.CONFLICT).json({ message: 'Duplicate email' });
  }

  user.name = name;
  user.email = email;

  if (password) {
    user.password = await bcrypt.hash(password, 10);
  }

  const updatedUser = await user.save();

  res.json({ message: `${updatedUser.name} updated` });
});

// Fonction pour supprimer un utilisateur
const deleteUser = asyncHandler(async (req, res) => {
  const { id } = req.body;

  if (!id) {
    return res.status(StatusCodes.BAD_REQUEST).json({ message: 'User ID required' });
  }

  const user = await User.findById(id).exec();

  if (!user) {
    return res.status(StatusCodes.BAD_REQUEST).json({ message: 'User not found' });
  }

  const result = await user.deleteOne();

  const reply = `User with ID ${result._id} deleted`;

  res.json(reply);
});






module.exports = {
  getAllUsers,
  createNewUser,
  updateUser,
  deleteUser,
  
};
