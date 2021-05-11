<#import "template.ftl" as template />

<#-- @ftlvariable name="entries" type="kotlin.collections.List<com.jetbrains.handson.model.BlogEntry>" -->

<@template.main_layout>
    <#list entries as item>
        <@template.view_entry entry=item standalone=false></@template.view_entry>
        <hr>
    </#list>
    <div>
        <a href="/new"><h4>[add a new journal entry]</h4></a>
    </div>
</@template.main_layout>