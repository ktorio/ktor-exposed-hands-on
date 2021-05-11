<#macro main_layout>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <title>Kotlin Journal</title>
    </head>
    <body style="text-align: center; font-family: sans-serif">
    <img src="/static/ktor.png">
    <h1>Kotlin Ktor Journal </h1>
    <p><i>Powered by Ktor, kotlinx.html & Freemarker!</i></p>
    <hr>

    <#nested />

    </body>
    </html>
</#macro>

<#macro back>
    <h4><a href="/">[back]</a></h4>
</#macro>

<#macro view_entry entry standalone>
<#-- @ftlvariable name="entry" type="com.jetbrains.handson.model.BlogEntry" -->
    <table style="margin-left:auto;margin-right:auto;">
        <tr>
            <td>
                <h3>
                    <#if standalone>
                        ${entry.headline}
                    <#else>
                        <a href="/entry/${entry.id}">${entry.headline}</a>
                    </#if>

                </h3>
            </td>
            <#if standalone>
                <td>
                    <form action="/submit" method="post">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="id" value="${entry.id}"/>
                        <input type="image" src="/static/delete.png" width="12" alt="Delete"/>
                    </form>
                </td>
            </#if>
        </tr>
        <tr>
            <td>
                <p>${entry.body}</p>
            </td>
        </tr>
    </table>
</#macro>