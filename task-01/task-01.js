use vemser_tf_4

db.createCollection("produtos")

db.produtos.insertMany([
  {
      modelo: "MANGALONGA", tamanho: "P",
      cor: "Branco",
      descricao: "Camisa estampa star wars",
      setor: "MASCULINO",
      valor: 25.90,
      imgUrl: "https://www.imagemteste.com/imagemTeste"
  },
  {
      modelo: "REGATA",
      tamanho: "M",
      cor: "Preto",
      descricao: "Camisa estampa tropical",
      setor: "INFANTIL",
      valor: 15.90,
      imgUrl: "https://www.imagemteste.com/imagemTeste"
  },
  {
      modelo: "MANGA",
      tamanho: "G",
      cor: "Branco",
      descricao: "Camisa lisa",
      setor: "MASCULINO",
      valor: 5.90,
      imgUrl: "https://www.imagemteste.com/imagemTeste"
  },
  {
      modelo: "CAMISETA",
      tamanho: "P",
      cor: "AZUL",
      descricao: "Camisa lisa",
      setor: "FEMININO",
      valor: 25.90,
      imgUrl: "https://www.imagemteste.com/imagemTeste"
  },
  {
      modelo: "CAMISETA",
      tamanho: "G",
      cor: "CINZA",
      descricao: "Camisa estampa flores",
      setor: "FEMININO",
      valor: 15.90,
      imgUrl: "https://www.imagemteste.com/imagemTeste"
  }
]);



//consulta 01
db.produtos.find({ valor: { $lt: 10.00 } })
//retorno
{
  _id: ObjectId("64e5667be67ce5416961009c"),
  modelo: 'MANGA',
  tamanho: 'G',
  cor: 'Branco',
  descricao: 'Camisa lisa',
  setor: 'MASCULINO',
  valor: 5.9,
  imgUrl: 'https://www.imagemteste.com/imagemTeste'
}


//consulta 02
db.produtos.find({ modelo: "MANGALONGA" })
//retorno
{
  _id: ObjectId("64e5667be67ce5416961009a"),
  modelo: 'MANGALONGA',
  tamanho: 'P',
  cor: 'Branco',
  descricao: 'Camisa estampa star wars',
  setor: 'MASCULINO',
  valor: 25.9,
  imgUrl: 'https://www.imagemteste.com/imagemTeste'
}


//consulta 03
db.produtos.find({ setor: "MASCULINO", valor: { $lte: 30.00 } })
//retorno
{
  _id: ObjectId("64e5667be67ce5416961009a"),
  modelo: 'MANGALONGA',
  tamanho: 'P',
  cor: 'Branco',
  descricao: 'Camisa estampa star wars',
  setor: 'MASCULINO',
  valor: 25.9,
  imgUrl: 'https://www.imagemteste.com/imagemTeste'
}
{
  _id: ObjectId("64e5667be67ce5416961009c"),
  modelo: 'MANGA',
  tamanho: 'G',
  cor: 'Branco',
  descricao: 'Camisa lisa',
  setor: 'MASCULINO',
  valor: 5.9,
  imgUrl: 'https://www.imagemteste.com/imagemTeste'
}


//consulta 04
db.produtos.find().sort({ valor: -1 })
//retorno
{
  _id: ObjectId("64e5667be67ce5416961009a"),
  modelo: 'MANGALONGA',
  tamanho: 'P',
  cor: 'Branco',
  descricao: 'Camisa estampa star wars',
  setor: 'MASCULINO',
  valor: 25.9,
  imgUrl: 'https://www.imagemteste.com/imagemTeste'
}
{
  _id: ObjectId("64e5667be67ce5416961009d"),
  modelo: 'CAMISETA',
  tamanho: 'P',
  cor: 'AZUL',
  descricao: 'Camisa lisa',
  setor: 'FEMININO',
  valor: 25.9,
  imgUrl: 'https://www.imagemteste.com/imagemTeste'
}
{
  _id: ObjectId("64e5667be67ce5416961009b"),
  modelo: 'REGATA',
  tamanho: 'M',
  cor: 'Preto',
  descricao: 'Camisa estampa tropical',
  setor: 'INFANTIL',
  valor: 15.9,
  imgUrl: 'https://www.imagemteste.com/imagemTeste'
}
{
  _id: ObjectId("64e5667be67ce5416961009e"),
  modelo: 'CAMISETA',
  tamanho: 'G',
  cor: 'CINZA',
  descricao: 'Camisa estampa flores',
  setor: 'FEMININO',
  valor: 15.9,
  imgUrl: 'https://www.imagemteste.com/imagemTeste'
}
{
  _id: ObjectId("64e5667be67ce5416961009c"),
  modelo: 'MANGA',
  tamanho: 'G',
  cor: 'Branco',
  descricao: 'Camisa lisa',
  setor: 'MASCULINO',
  valor: 5.9,
  imgUrl: 'https://www.imagemteste.com/imagemTeste'
}