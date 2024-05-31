require('dotenv').config();
const passport = require('passport');
const StrategyJwt = require('passport-jwt').Strategy;
const { ExtractJwt } = require('passport-jwt');
const dbPool = require('../config/database');

passport.use(
  new StrategyJwt(
    {
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      secretOrKey: process.env.JWT_SECRET,
    },
    ((jwtPayload, done) => dbPool.execute(`SELECT * FROM users where id = ${jwtPayload.id}`)
      .then((user) => done(null, user))
      .catch((error) => done(error))),
  ),
);
