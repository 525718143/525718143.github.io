<template>
 <div>
   <Header/>
   <div style="display: flex">
     <Aside/>
    <router-view style="flex: 1"/>
   </div>
 </div>
</template>

<style>
</style>
<script>
 import Header from "@/components/Header";
 import Aside from "@/components/Aside";
 export default {
   name: "Layout",
   components:{
     Header,
     Aside
   }
 }
</script>
