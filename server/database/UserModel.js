const mongoose = require('mongoose');

const UserSchema = new mongoose.Schema({
    phone:{
        type: Number,
        require: true,
    }, 
    username:{
        type: String,
        require: true,
    },
    password:{
        type: String,
        require: true,
    },
    address:{
        type: String
    },
    role:{
        type: String,
        require: true
    }
});
const UserModel = mongoose.model('user', UserSchema);
module.exports = UserModel;