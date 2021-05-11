<#import "template.ftl" as template />

<#-- @ftlvariable name="entry" type="com.jetbrains.handson.model.BlogEntry" -->

<@template.main_layout>
    <@template.view_entry entry=entry standalone=true></@template.view_entry>
    <hr>
    <@template.back></@template.back>
</@template.main_layout>