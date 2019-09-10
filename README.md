# ClickListApp
App Android Didático: Listview com ações. Este app implementa um simples cadastro de pessoas. Porém, o acesso às funcionalidades Excluir e Alterar são feitos clicando em imagens no item da listview.

<table>
<tr align=center>
<td><img src="https://github.com/machadowma/ClickListApp/blob/master/main.png" align="left" height="360" width="180" ></td>
<td><img src="https://github.com/machadowma/ClickListApp/blob/master/delete.png" align="left" height="360" width="180" ></td>
</tr>
<tr align=center>
<td>Tela Inicial</td>
<td>Excluindo registros</td>
</tr>
</table>

# Implementando a ação Excluir
A implementação do evento onClick da imagem relacionada a exclusão foi realizada no Adapter.
1. Inicialmente, um AlertDialog é exibido para solicitar confirmação do usuário para a exclusão.
2. Em caso afirmativo, o id da pessoa contida na lista de dados na posição clicada é enviado para o método que realizará e exclusão no banco de dados.
3. Em seguida, o elemento da lista é removido.
4. Finalmente, notifyDataSetChanged é invocado para atualizar a listview.
```
holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alerta;
                AlertDialog.Builder builder = new AlertDialog.Builder(layoutInflater.getContext());
                builder.setTitle("Excluir");
                builder.setMessage("Deseja realmente excluir?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        removerPessoa(listData.get(position).getId());
                        listData.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

                alerta = builder.create();
                alerta.show();


            }
        });
```
O método que realiza a exclusão do dado na banco de dados neste exemplo também está implementado no Adapter. 
Um detalhe importante é que, nesse exemplo, é necessário utilizar o Context da Activity que invoca Adapter, a partir de layoutInflater.getContext(). 
```
public void removerPessoa(Integer id){
        try {
            bancoDados = layoutInflater.getContext().openOrCreateDatabase("clicklist",layoutInflater.getContext().MODE_PRIVATE ,null);
            String sql = "DELETE FROM pessoa WHERE id = ?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindLong(1, id);
            stmt.executeUpdateDelete();
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
```

# Implementando a ação Alterar
A implementação do evento onClick da imagem relacionada a alteração foi realizada no Adapter.
Nesse caso, uma nova Activity é criada para exibir as opções de edição.
Um detalhe importante é que, nesse exemplo, é necessário utilizar o Context da Activity que invoca Adapter, a partir de layoutInflater.getContext(). 
```
public void alterarPessoa(Integer id){
        Intent intent = new Intent(layoutInflater.getContext(),EditActivity.class);
        intent.putExtra("id",id);
        layoutInflater.getContext().startActivity(intent);
    }
```
   
# License
MIT License

Copyright (c) 2019 machadowma

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
