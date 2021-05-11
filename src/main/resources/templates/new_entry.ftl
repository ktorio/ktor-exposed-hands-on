<#import "template.ftl" as template />

<@template.main_layout>
    <div>
        <h3>Add a new journal entry</h3>
        <form action="/submit" method="post">
            <input type="text" name="headline">
            <input type="hidden" name="action" value="add"/>
            <br>
            <textarea name="body"></textarea>
            <br>
            <input type="submit">
        </form>
    </div>
    <hr>
    <@template.back></@template.back>
</@template.main_layout>