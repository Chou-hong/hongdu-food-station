<template>
  <div>
    <div class="logo">
      <!-- <img
        src="./../../../assets/logo.png"
        width="122.5"
        alt=""
      > -->
      <!-- <img
        src="@/assets/login/login-logo.png"
        alt=""
        style="width: 120px; height: 31px"
      /> -->
      <div v-if="!isCollapse" class="sidebar-logo">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 150 34" width="130" height="30">
          <circle cx="17" cy="17" r="14" fill="#C9A961" stroke="#F5E6D3" stroke-width="1"/>
          <path d="M8 19 Q17 26 26 19 L24 21 Q17 26 10 21 Z" fill="#8B1A1A"/>
          <ellipse cx="17" cy="19" rx="9" ry="2" fill="#F5E6D3"/>
          <path d="M13 14 Q14 11 13 8" stroke="#F5E6D3" stroke-width="1.1" fill="none" stroke-linecap="round"/>
          <path d="M17 13 Q18 10 17 7" stroke="#F5E6D3" stroke-width="1.1" fill="none" stroke-linecap="round"/>
          <path d="M21 14 Q22 11 21 8" stroke="#F5E6D3" stroke-width="1.1" fill="none" stroke-linecap="round"/>
          <text x="36" y="21" font-family="Microsoft YaHei, sans-serif" font-size="15" font-weight="bold" fill="#FFFFFF">红都食驿</text>
          <text x="36" y="31" font-family="Arial, sans-serif" font-size="5.5" fill="#E8D5A8" letter-spacing="0.8">HONGDU FOOD STATION</text>
        </svg>
      </div>
      <div v-else class="sidebar-logo-mini">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 30 30" width="30" height="30">
          <circle cx="15" cy="15" r="13" fill="#C9A961" stroke="#F5E6D3" stroke-width="1"/>
          <path d="M7 17 Q15 24 23 17 L21 19 Q15 24 9 19 Z" fill="#8B1A1A"/>
          <ellipse cx="15" cy="17" rx="8" ry="2" fill="#F5E6D3"/>
          <path d="M12 12 Q13 9 12 6" stroke="#F5E6D3" stroke-width="1" fill="none" stroke-linecap="round"/>
          <path d="M15 11 Q16 8 15 5" stroke="#F5E6D3" stroke-width="1" fill="none" stroke-linecap="round"/>
          <path d="M18 12 Q19 9 18 6" stroke="#F5E6D3" stroke-width="1" fill="none" stroke-linecap="round"/>
        </svg>
      </div>
    </div>
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu :default-openeds="defOpen"
               :default-active="defAct"
               :collapse="isCollapse"
               :background-color="variables.menuBg"
               :text-color="variables.menuText"
               :active-text-color="variables.menuActiveText"
               :unique-opened="false"
               :collapse-transition="false"
               mode="vertical">
        <sidebar-item v-for="route in routes"
                      :key="route.path"
                      :item="route"
                      :base-path="route.path"
                      :is-collapse="isCollapse" />
        <!-- <div class="sub-menu">
          <div class="avatarName">
            {{ name }}
          </div>
          <div class="img">
            <img
              src="./../../../assets/icons/btn_close@2x.png"
              class="outLogin"
              alt="退出"
              @click="logout"
            />
          </div>
        </div> -->
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { AppModule } from '@/store/modules/app'
import { UserModule } from '@/store/modules/user'
import SidebarItem from './SidebarItem.vue'
import variables from '@/styles/_variables.scss'
import { getSidebarStatus, setSidebarStatus } from '@/utils/cookies'
import Cookies from 'js-cookie'
@Component({
  name: 'SideBar',
  components: {
    SidebarItem
  }
})
export default class extends Vue {
  private restKey: number = 0
  get name() {
    return (UserModule.userInfo as any).name
      ? (UserModule.userInfo as any).name
      : JSON.parse(Cookies.get('user_info') as any).name
  }
  get defOpen() {
    // const urlArr = this.$route.path.split('/')
    // const openStr = urlArr.length > 2 ? `/${urlArr[1]}` : '/'
    let path = ['/']
    this.routes.forEach((n: any, i: number) => {
      if (n.meta.roles && n.meta.roles[0] === this.roles[0]) {
        path.splice(0, 1, n.path)
      }
    })
    return path
  }

  get defAct() {
    let path = this.$route.path
    return path
  }

  get sidebar() {
    return AppModule.sidebar
  }

  get roles() {
    return UserModule.roles
  }

  get routes() {
    let routes = JSON.parse(
      JSON.stringify([...(this.$router as any).options.routes])
    )
    console.log('-=-=routes=-=-=', routes)
    console.log('-=-=routes=-=-=', this.roles[0])
    let menuList = []
    let menu = routes.find(item => item.path === '/')
    if (menu) {
      menuList = menu.children
    }
    console.log('-=-=routes=-wwww=-=', routes)
    return menuList
  }

  get variables() {
    return variables
  }

  get isCollapse() {
    return !this.sidebar.opened
  }
  private async logout() {
    this.$store.dispatch('LogOut').then(() => {
      // location.href = '/'
      this.$router.replace({ path: '/login' })
    })
    // this.$router.push(`/login?redirect=${this.$route.fullPath}`)
  }
}
</script>

<style lang="scss" scoped>
.logo {
  text-align: center;
  background-color: #8b1a1a;
  padding: 15px 0 0;
  height: 60px;
  img {
    display: inline-block;
  }
}
.sidebar-logo-mini {
  img {
    width: 30px;
    height: 30px;
  }
}
.el-scrollbar {
  height: 100%;
  background-color: rgb(52, 55, 68);
}

.el-menu {
  border: none;
  height: calc(95vh - 23px);
  width: 100% !important;
  padding: 47px 15px 0;
}
</style>
