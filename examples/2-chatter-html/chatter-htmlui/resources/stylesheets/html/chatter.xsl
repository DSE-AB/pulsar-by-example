<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" indent="no" encoding="UTF-8"/>
    <xsl:param name="action"/>

    <xsl:template match="chatter">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="messages">
        <table>
            <tr><th>User</th><th>Message</th></tr>
            <xsl:apply-templates/>
        </table>
    </xsl:template>

    <xsl:template match="message">
        <tr>
        <td>
            <xsl:value-of select="@userId"/>
        </td>
        <td>
            <xsl:value-of select="content"/>
        </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>

