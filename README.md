# Proyecto: Billetera Virtual

## Descripción
La **Billetera Virtual** es una aplicación diseñada para gestionar de manera eficiente las transacciones financieras personales y ofrecer una visión clara de los presupuestos y gastos. La aplicación permite a los usuarios realizar diversas operaciones financieras como depósitos, retiros y transferencias de dinero, así como mantener un seguimiento de sus cuentas y categorías de gasto. Además, genera informes financieros y reportes para una mejor planificación y control financiero.

## Principales Funcionalidades
1. **Gestión de Usuarios:**
   - Crear y actualizar perfiles de usuario.
   - Eliminar usuarios y validar la existencia de usuarios.

2. **Transacciones Financieras:**
   - Realizar depósitos, retiros y transferencias de dinero.
   - Consultar y listar transacciones por usuario.
   - Filtrar transacciones por fecha y obtener detalles específicos de transacciones.

3. **Gestión de Presupuestos:**
   - Crear, actualizar y eliminar presupuestos.
   - Consultar el monto gastado y el saldo restante de los presupuestos.
   - Calcular el gasto total por categoría de manera recursiva.

4. **Cuentas de Usuario:**
   - Agregar, actualizar y eliminar cuentas bancarias asociadas a los usuarios.
   - Consultar detalles de las cuentas de usuario.

5. **Categorías de Gasto:**
   - Crear, actualizar y eliminar categorías de gasto.
   - Asignar categorías a transacciones y listar categorías disponibles.

6. **Informes y Estadísticas:**
   - Generar reportes financieros en formatos CSV y PDF.
   - Consultar los gastos más comunes y los usuarios con más transacciones.
   - Calcular el saldo promedio de los usuarios.
   - Iniciar la generación de informes financieros detallados.

7. **Persistencia y Serialización:**
   - Guardar y recuperar datos de usuarios, transacciones, presupuestos y cuentas usando serialización.
   - Mantener copias de seguridad de los datos.

## Tecnologías Utilizadas
- **Java:** Lenguaje de programación principal.
- **JAXB:** Para la serialización y deserialización de datos en formato XML.
- **Logger:** Para registrar eventos y mensajes de la aplicación.
- **Serializable:** Para la persistencia de objetos.

## Uso de Hilos
- Implementación de hilos para la generación de informes financieros, mejorando la eficiencia y el rendimiento de la aplicación mediante la ejecución de tareas en segundo plano.

---


