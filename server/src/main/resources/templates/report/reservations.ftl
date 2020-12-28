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

    .PENDING {
        color: #ff0000;
    }

    .APPROVED, .IN_PROGRESS, .COMPLETED {
        color: #42f554;
    }

    .REJECTED {
        color: #b3b3b3;
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
            <tr>
                <td style="text-align: center">${reservation.id}</td>
                <td>${reservation.activeFrom}</td>
                <td>${reservation.activeUntil}</td>
                <td style="text-align: center">${reservation.tableNumber}</td>
                <td class="${reservation.status}">${reservation.statusDescription}</td>
                <#if reservation.manager??>
                    <#assign managerProfile=reservation.manager.user/>
                    <td>${managerProfile.firstName} ${managerProfile.lastName}</td>
                <#else>
                    <td><i>Не назначен</i></td>
                </#if>
                <td>${customerProfile.firstName} ${customerProfile.lastName}</td>
            </tr>
        </#list>
    </table>
</#if>
