<#-- @ftlvariable name="reservations" type="kotlin.collections.List<org.itmodreamteam.myrest.server.service.reservation.ReservationReportServiceImpl.ReservationEntry>" -->
<#-- @ftlvariable name="date" type="String" -->

<style>
    * {
        font-family: "Liberation Sans", sans-serif;
    }
</style>

<#if reservations?size = 0>
    <h1>Бронирований на ${date} не найдено</h1>
<#else>
    <h1>Бронирования на ${date}</h1>
    <#list reservations as reservation>
        ${reservation.id}
    </#list>
</#if>
