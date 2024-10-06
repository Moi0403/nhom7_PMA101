const mongoose = require('mongoose');

const GioHangSchema = new mongoose.Schema({
    maUser: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'user',
        require: true
    },
    maSP: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'sanpham', 
        require: true
    },
    soLuong: {
        type: Number,
        require: true,
        min: 1
    },
    trangThaiMua: {
        type: Number,
        require: true
    }
}, {
    timestamps: true 
});

const GioHangModel = mongoose.model('giohang', GioHangSchema);
module.exports = GioHangModel;
