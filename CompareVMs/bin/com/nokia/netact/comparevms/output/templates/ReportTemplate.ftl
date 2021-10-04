<html lang="en-us">
   <head>
      <title>${title}</title>
      <style>
         body, html
         {
         background-color: #F5F5F5;
         font-size: 12px;
         font-family: Verdana, Helvetica, Arial,sans-serif;
         scrollbar-base-color: #EAEAEA; scrollbar-arrow-color: #4095CB;
         scrollbar-3dlight-color: #93C6E7
         }
         table
         {
         font-size: 12px;
         font-family: Verdana, Helvetica, Arial,sans-serif;
         border-collapse: collapse;
         width: 80%;
         }
         table tr
         {
         background-color: #F5F5F5;
         }
         table th
         {
         background-color: #DFE5EF;
         }
         table td
         {
         font-family: Verdana, Helvetica, Arial;
         font-size-adjust: none;
         font-size: 13px;
         font-weight: bold;
         text-decoration: none;
         color: #1B1F6A;
         }
         h1
         {
         font-size: 19px;
         color: #496980;
         font-family: Verdana, Helvetica, Arial;
         }
         h2
         {
         font-size: 17px;
         color: #597990;
         font-family: Verdana, Helvetica, Arial;
         font-weight: bold;
         }
         h3
         {
         font-size: 15px;
         color: #597990;
         font-family: Verdana, Helvetica, Arial;
         font-weight: bold;
         }
      </style>
   </head>
   <body>
      <ul>
         <h1>${title}</h1>
         <ul>
            <#list output?keys as vmType>
            <h2>${vmType_index + 1}. ${vmType}</h2>
            <table border='1'>
               <tr>
                  <th>VM name</th>
                  <th>Attribute name</th>
                  <th>Match status</th>
                  <th>Error message</th>
               </tr
               <#list output[vmType]?keys as vmName>
               <#assign parameters = output[vmType][vmName]>
               <tr>
                  <td align='center' rowspan=${parameters?size}>${vmName}</td>
                  <#list parameters as parameter>      			
                  <td>${parameter.attribute}</td>
                  <#if parameter.matchSatus == 'MATCH'>
                  <td bgcolor='#7CFC00'>${parameter.matchSatus}</td>
                  <#elseif parameter.matchSatus == 'MISMATCH'>
                  <td bgcolor='#FF0000'>${parameter.matchSatus}</td>
                  </#if>
                  <td>${parameter.comment}</td>
               </tr>
               </#list>
            </#list>
            </table>
            <br>
            <hr>
            </#list>
         </ul>
      </ul>
   </body>
</html>