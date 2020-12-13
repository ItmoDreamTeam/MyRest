<#-- @ftlvariable name="reservations" type="kotlin.collections.List<org.itmodreamteam.myrest.server.service.reservation.ReservationReportServiceImpl.ReservationEntry>" -->
<#-- @ftlvariable name="date" type="String" -->

<style>
    * {
        font-family: "Liberation Sans", sans-serif;
    }

    table {
        border: 1px;
    }

    th {
        border: 1px;
        text-align: center;
        padding: 2px;
    }

    td {
        border: 1px;
        padding: 2px;
    }
</style>

<#if reservations?size = 0>
    <h1>Бронирований на ${date} не найдено</h1>
<#else>
    <h1>Бронирования на ${date}</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>С</th>
            <th>По</th>
            <th>Столик</th>
            <th>Статус</th>
            <th>Менеджер</th>
            <th>Посетитель</th>
        </tr>
        <#list reservations as reservation>
            <#assign customerProfile=reservation.user/>
            <#if reservation.manager??>
                <#assign managerProfile=reservation.manager.user/>
            </#if>
            <tr>
                <td>${reservation.id}</td>
                <td>${reservation.activeFrom}</td>
                <td>${reservation.activeUntil}</td>
                <td>${reservation.table.info.number}</td>
                <td>${reservation.statusDescription}</td>
                <#if managerProfile??>
                    <td>${managerProfile.firstName} ${managerProfile.lastName}</td>
                <#else>
                    <td>Не назначен</td>
                </#if>
                <td>${customerProfile.firstName} ${customerProfile.lastName}</td>
            </tr>
        </#list>
    </table>
</#if>
