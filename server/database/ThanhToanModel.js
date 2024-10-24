const mongoose = require('mongoose');

const ThanhToanSchema = new mongoose.Schema({
    maUser: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'user',
        require: true
    },
    tenSP: {
        type: String,
        required: true
    },
    anhSP: {
        type: String,
        required: true
    },
    giaSP: {
        type: Number,
        required: true
    },
    soLuong: {
        type: Number,
        required: true,
        min: 1 // Số lượng ít nhất là 1
    },
    ngayMua: {
        type: Date,
        default: Date.now
    },
    trangthaiTT: {
        type: Number,
        require: true
    }
});
const ThanhToanModel = mongoose.model('thanhtoan', ThanhToanSchema);
module.exports = ThanhToanModel;