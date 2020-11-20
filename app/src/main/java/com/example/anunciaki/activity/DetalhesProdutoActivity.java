package com.example.anunciaki.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anunciaki.R;
import com.example.anunciaki.model.Anuncio;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class DetalhesProdutoActivity extends AppCompatActivity {

  private CarouselView carouselView;
  private TextView titulo;
  private TextView descricao;
  private TextView estado;
  private TextView preco;
  private Anuncio anuncioSelecionado;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detalhes_produto);

    //Configurar toolbar
    getSupportActionBar().setTitle("Detalhe produto");


    //Incializar componentes de interface
    inicializarComponentes();

    //Recupera anúncio para exibicao
    anuncioSelecionado = (Anuncio) getIntent().getSerializableExtra("anuncioSelecionado");

    if( anuncioSelecionado != null ){

      titulo.setText( anuncioSelecionado.getTitulo() );
      descricao.setText( anuncioSelecionado.getDescricao() );
      estado.setText( anuncioSelecionado.getEstado() );
      preco.setText( anuncioSelecionado.getValor());

      ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
          String urlString = anuncioSelecionado.getFotos().get( position );
          Picasso.get().load(urlString).into(imageView);
        }
      };

      carouselView.setPageCount( anuncioSelecionado.getFotos().size() );
      carouselView.setImageListener( imageListener );

    }
  }

  public void ligarTelefone(View view){
    Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", anuncioSelecionado.getTelefone(), null ));
    startActivity( i );
  }

  public void whatsApp(View view){
    String url = "https://api.whatsapp.com/send?phone=" + "+55" + anuncioSelecionado.getTelefone();
    Intent intentSuporte = new Intent(Intent.ACTION_VIEW);
    intentSuporte.setData(Uri.parse(url));
    startActivity(intentSuporte);
  }


  private void inicializarComponentes(){
    carouselView = findViewById(R.id.carouselView);
    titulo = findViewById(R.id.textTituloDetalhe);
    descricao = findViewById(R.id.textDescricaoDetalhe);
    estado = findViewById(R.id.textEstadoDetalhe);
    preco = findViewById(R.id.textPrecoDetalhe);
  }
}