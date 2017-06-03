<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output omit-xml-declaration="yes" indent="yes"/>
    <xsl:strip-space elements="*"/>

    <xsl:template match="/pedido">
        <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:nota="http://nota.com.br">
           <soap:Body>
                <nota:nota>
                    <nota:data><xsl:value-of select="data"/></nota:data>
                    <nota:valor><xsl:value-of select="pagamento/valor"/></nota:valor>
                    <nota:itens>
                        <xsl:for-each select="itens/item">
                            <nota:item><xsl:value-of select="quantidade"/> - <xsl:value-of select="formato"/> - <xsl:value-of select="livro/codigo"/></nota:item>
                        </xsl:for-each>
                    </nota:itens>
                 </nota:nota>
            </soap:Body>
        </soap:Envelope>        
    </xsl:template>

</xsl:stylesheet>