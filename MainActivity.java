package com.example.karaoke;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtNama, edtLamaSewa;
    Spinner spinnerRuangan;
    Button btnHitung, btnReset;
    TextView txtHasil;

    // Array harga ruangan
    int[] hargaRuangan = {50000, 75000, 100000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hubungkan XML dengan Java
        edtNama = findViewById(R.id.edtNama);
        edtLamaSewa = findViewById(R.id.edtLamaSewa);
        spinnerRuangan = findViewById(R.id.spinnerRuangan);
        btnHitung = findViewById(R.id.btnHitung);
        btnReset = findViewById(R.id.btnReset);
        txtHasil = findViewById(R.id.txtHasil);

        // Daftar ruangan
        String[] namaRuangan = {
                "Small - Rp50.000/jam",
                "Medium - Rp75.000/jam",
                "Large - Rp100.000/jam"
        };

        // Menampilkan daftar ruangan
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                namaRuangan
        );

        spinnerRuangan.setAdapter(adapter);

        // Tombol Hitung
        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama = edtNama.getText().toString();
                String lamaInput = edtLamaSewa.getText().toString();

                // IF ELSE untuk mengecek input
                if (nama.isEmpty() || lamaInput.isEmpty()) {

                    Toast.makeText(
                            MainActivity.this,
                            "Data harus diisi!",
                            Toast.LENGTH_SHORT
                    ).show();

                } else {

                    int lamaSewa = Integer.parseInt(lamaInput);

                    if (lamaSewa <= 0) {

                        Toast.makeText(
                                MainActivity.this,
                                "Lama sewa harus lebih dari 0!",
                                Toast.LENGTH_SHORT
                        ).show();

                    } else {

                        // Posisi ruangan yang dipilih
                        int posisi = spinnerRuangan.getSelectedItemPosition();

                        /*
                         * FOR LOOP
                         * Mencari harga berdasarkan posisi ruangan
                         */
                        int harga = 0;

                        for (int i = 0; i < hargaRuangan.length; i++) {

                            if (i == posisi) {
                                harga = hargaRuangan[i];
                                break;
                            }
                        }

                        // Menghitung subtotal
                        int subtotal = harga * lamaSewa;

                        // Menghitung diskon
                        double diskon;

                        if (lamaSewa >= 5) {
                            diskon = subtotal * 0.10;
                        } else {
                            diskon = 0;
                        }

                        // Total pembayaran
                        double totalBayar = subtotal - diskon;

                        // Menentukan nama ruangan
                        String ruangan;

                        if (posisi == 0) {
                            ruangan = "Small";
                        } else if (posisi == 1) {
                            ruangan = "Medium";
                        } else {
                            ruangan = "Large";
                        }

                        // Menampilkan hasil
                        String hasil =
                                "===== DETAIL SEWA =====\n\n" +
                                        "Nama       : " + nama + "\n" +
                                        "Ruangan    : " + ruangan + "\n" +
                                        "Lama Sewa  : " + lamaSewa + " jam\n" +
                                        "Harga/Jam  : Rp" + formatRupiah(harga) + "\n" +
                                        "Subtotal   : Rp" + formatRupiah(subtotal) + "\n" +
                                        "Diskon     : Rp" + formatRupiah((int) diskon) + "\n" +
                                        "-------------------------\n" +
                                        "Total Bayar: Rp" + formatRupiah((int) totalBayar);

                        txtHasil.setText(hasil);
                    }
                }
            }
        });

        // Tombol Reset
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtNama.setText("");
                edtLamaSewa.setText("");
                spinnerRuangan.setSelection(0);

                txtHasil.setText(
                        "Hasil pembayaran akan muncul di sini"
                );
            }
        });
    }

    // Method format rupiah
    private String formatRupiah(int angka) {

        return String.format("%,d", angka)
                .replace(',', '.');
    }
}