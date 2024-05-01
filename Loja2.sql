USE SistemaComercial;

SELECT p.Nome AS Produto, SUM(v.Quantidade * v.PreçoUnitario) AS ValorTotal
FROM Vendas v
JOIN Produtos p
ON v.ProdutoID = p.ID
GROUP BY p.Nome;



