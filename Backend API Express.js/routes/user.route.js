const express = require('express');
const passport = require('passport');
const userController = require('../controllers/user.controller');

const router = express.Router();

router.post('/register', userController.register);
router.post('/login', userController.login);
router.put('/edit', passport.authenticate('jwt', { session: false }), userController.update);
router.get('/view', passport.authenticate('jwt', { session: false }), userController.viewUser);
router.put('/add-point', passport.authenticate('jwt', { session: false }), userController.addPoint);


module.exports = router;
