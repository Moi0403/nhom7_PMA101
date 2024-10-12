const mongoose = require('mongoose');

const HoaDonSchema = new mongoose.Schema({
    maUser: {
        type: mongoose.Schema.Types.ObjectId, 
        ref: 'user',
        required: true 
    },
    maSP: [{
        type: mongoose.Schema.Types.ObjectId, 
        ref: 'sanpham',
        required: true 
    }],
    maGH: {
        type: mongoose.Schema.Types.ObjectId, 
        ref: 'giohang',
        required: true 
    },
    ngayMua: {
        type: String, 
        required: true 
    },
    tongTien: {
        type: Number,
        required: true 
    },
    diaChi: {
        type: String, 
        required: true 
    },
    trangThaiDH: {
        type: Number,
        required: true 
    }
}, { timestamps: true });

const HoaDonModel = mongoose.model('hoadon', HoaDonSchema);
module.exports = HoaDonModel;
