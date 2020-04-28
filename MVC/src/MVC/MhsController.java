package MVC;

import java.awt.event.*;
import javax.swing.*;

public class MhsController {
    MhsModel mhsmodel;
    MhsView mhsview;
    MhsDAO mhsdao;
    static String nim;
    
    public MhsController(MhsModel mhsmodel, MhsView mhsview, MhsDAO mhsdao){
        this.mhsmodel = mhsmodel;
        this.mhsview = mhsview;
        this.mhsdao = mhsdao;
        
        if(mhsdao.getJmldata() != 0){
            String dataMahasiswa[][] = mhsdao.readMahasiswa();
            mhsview.tabel.setModel((new JTable(dataMahasiswa, mhsview.namaKolom)).getModel());
        } else {
            JOptionPane.showMessageDialog(null, "Data tidak ada!");
        }
        
        mhsview.simpan.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String nim = mhsview.getNim();
                String nama = mhsview.getNama();
                String alamat = mhsview.getAlamat();
                
                if(nim.isEmpty() || nama.isEmpty() || alamat.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Harap isi semua field");
                } else {
                    mhsmodel.setMhsModel(nim, nama, alamat);
                    mhsdao.Insert(mhsmodel);
                    
                    String dataMahasiswa[][] = mhsdao.readMahasiswa();
                    mhsview.tabel.setModel((new JTable (dataMahasiswa, mhsview.namaKolom)).getModel());
                }
            }
        });
        
        /* sebenernya mau pake ini tapi masih eror terus bingung benerinnya, ga jadi deh :(
        mhsview.tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int baris = mhsview.tabel.getSelectedRow();
                int kolom = mhsview.tabel.getSelectedColumn();

                String dataTerpilih = mhsview.tabel.getValueAt(baris,1).toString();
                
                mhsview.delete.addActionListener((java.awt.event.ActionEvent f) -> {
                    int input = JOptionPane.showConfirmDialog(null, "Apakah anda ingin menghapus data " + dataTerpilih + "?","Delete Data",JOptionPane.YES_NO_OPTION);

                if (input == 0){
                    mhsdao.Delete(mhsmodel);
                    
                    String dataMahasiswa[][] = mhsdao.readMahasiswa();
                    mhsview.tabel.setModel(new JTable(dataMahasiswa, mhsview.namaKolom).getModel());
                }
                });
                
            }
        });*/
        
        mhsview.tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tabelMouseClicked(e);
                mhsview.delete.setEnabled(true);
            }
        });

        mhsview.delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //memasukkan data ke dalam Model
                mhsmodel.setNim(nim);
                
                int input = JOptionPane.showConfirmDialog(null, "Apakah anda ingin menghapus data NIM " + mhsmodel.getNim() + "?","Delete Data",JOptionPane.YES_NO_OPTION);
                
                if (input == 0){
                //menjalankan perintah delete di DAO
                mhsdao.Delete(mhsmodel);
                
                String dataMahasiswa[][] = mhsdao.readMahasiswa();
                mhsview.tabel.setModel((new JTable(dataMahasiswa, mhsview.namaKolom)).getModel());
                }
            }
        });
        
    }
    
    private void tabelMouseClicked(MouseEvent e) {
        int baris = mhsview.tabel.rowAtPoint(e.getPoint());
        nim = mhsview.tabel.getValueAt(baris,0).toString();
    }
}
