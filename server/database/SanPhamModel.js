const mongoose = require('mongoose');

const sanPhamSchema = new mongoose.Schema({
    anhSP: {
        type: String,
        required: true,
    },
    tenSP: {
        type: String,
        required: true, 
    },
    phanloaiSP: {
        type: String,
        required: true, 
    },
    trongluongSP: {
        type: Number,
        required: true, 
    },
    giaSP: {
        type: Number,
        required: true, 
    },
    motaSP: {
        type: String,
        required: true,
    }
});

const SanPhamModel = mongoose.model('sanpham', sanPhamSchema);

module.exports = SanPhamModel;
