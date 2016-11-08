CREATE VIEW Company as
SELECT	com.SCN_CODIGO as id, com.SCN_NAME as name, sci.SCI_URL_INDEX as urlIndex, scq.SCQ_URL_QUOTE as urlQuote FROM		indexyahoocfd.iyc_stack_company_index sci  INNER JOIN  indexyahoocfd.iyc_stock_companies com   on com.SCN_CODIGO = sci.SCN_CODIGO inner join indexyahoocfd.iyc_stack_company_quotes scq	on scq.scn_codigo = com.scn_codigo
