<template>
  <div class="login">
    <div class="login-box">
      <!-- 左侧品牌装饰面板 -->
      <div class="login-visual">
        <svg class="visual-bg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 495 480" preserveAspectRatio="xMidYMid slice">
          <defs>
            <linearGradient id="hongduGrad" x1="0%" y1="0%" x2="100%" y2="100%">
              <stop offset="0%" style="stop-color:#8B1A1A"/>
              <stop offset="50%" style="stop-color:#A0282A"/>
              <stop offset="100%" style="stop-color:#6B1414"/>
            </linearGradient>
            <radialGradient id="glowGrad" cx="50%" cy="38%" r="55%">
              <stop offset="0%" style="stop-color:#C9A961;stop-opacity:0.18"/>
              <stop offset="100%" style="stop-color:#C9A961;stop-opacity:0"/>
            </radialGradient>
          </defs>
          <rect width="495" height="480" fill="url(#hongduGrad)"/>
          <rect width="495" height="480" fill="url(#glowGrad)"/>
          <!-- 背景装饰几何 -->
          <g opacity="0.07" fill="#C9A961">
            <polygon points="40,40 55,70 25,70"/>
            <polygon points="450,410 465,440 435,440"/>
            <polygon points="60,430 75,460 45,460"/>
            <circle cx="440" cy="60" r="18"/>
            <circle cx="70" cy="180" r="10"/>
            <polygon points="420,200 430,220 410,220"/>
          </g>
          <!-- 中央碗形图标 -->
          <g transform="translate(247, 175)">
            <circle cx="0" cy="0" r="68" fill="none" stroke="#C9A961" stroke-width="1.5" opacity="0.35"/>
            <circle cx="0" cy="0" r="52" fill="#C9A961" opacity="0.92"/>
            <path d="M-33 4 Q0 28 33 4 L28 11 Q0 30 -28 11 Z" fill="#8B1A1A"/>
            <ellipse cx="0" cy="4" rx="33" ry="6.5" fill="#F5E6D3"/>
            <path d="M-14 -14 Q-11 -24 -14 -33" stroke="#F5E6D3" stroke-width="2.5" fill="none" stroke-linecap="round" opacity="0.85"/>
            <path d="M0 -17 Q3 -27 0 -36" stroke="#F5E6D3" stroke-width="2.5" fill="none" stroke-linecap="round" opacity="0.85"/>
            <path d="M14 -14 Q17 -24 14 -33" stroke="#F5E6D3" stroke-width="2.5" fill="none" stroke-linecap="round" opacity="0.85"/>
          </g>
          <!-- 品牌文字 -->
          <text x="247" y="295" text-anchor="middle" font-family="Microsoft YaHei, PingFang SC, sans-serif" font-size="34" font-weight="bold" fill="#FFFFFF" letter-spacing="10">红都食驿</text>
          <text x="247" y="322" text-anchor="middle" font-family="Arial, sans-serif" font-size="10" fill="#E8D5A8" letter-spacing="3.5">HONGDU FOOD STATION</text>
          <line x1="187" y1="342" x2="307" y2="342" stroke="#C9A961" stroke-width="1" opacity="0.5"/>
          <text x="247" y="368" text-anchor="middle" font-family="Microsoft YaHei, PingFang SC, sans-serif" font-size="13" fill="#E8D5A8" letter-spacing="5">瑞金味道 · 红色传承</text>
        </svg>
      </div>
      <!-- 右侧登录表单 -->
      <div class="login-form">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules">
          <div class="login-form-title">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 170 40" width="150" height="36">
              <circle cx="20" cy="20" r="16" fill="#B22222" stroke="#C9A961" stroke-width="1.5"/>
              <path d="M10 23 Q20 31 30 23 L28 26 Q20 32 12 26 Z" fill="#C9A961"/>
              <ellipse cx="20" cy="23" rx="10" ry="2.5" fill="#F5E6D3"/>
              <path d="M15 17 Q16 13 15 9" stroke="#C9A961" stroke-width="1.3" fill="none" stroke-linecap="round"/>
              <path d="M20 16 Q21 12 20 8" stroke="#C9A961" stroke-width="1.3" fill="none" stroke-linecap="round"/>
              <path d="M25 17 Q26 13 25 9" stroke="#C9A961" stroke-width="1.3" fill="none" stroke-linecap="round"/>
              <text x="44" y="25" font-family="Microsoft YaHei, PingFang SC, sans-serif" font-size="18" font-weight="bold" fill="#8B1A1A">红都食驿</text>
              <text x="44" y="36" font-family="Arial, sans-serif" font-size="6.5" fill="#C9A961" letter-spacing="0.8">HONGDU FOOD STATION</text>
            </svg>
          </div>
          <p class="login-subtitle">管理后台登录</p>
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              type="text"
              auto-complete="off"
              placeholder="请输入账号"
              prefix-icon="iconfont icon-user"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="iconfont icon-lock"
              @keyup.enter.native="handleLogin"
            />
          </el-form-item>
          <el-form-item style="width: 100%">
            <el-button
              :loading="loading"
              class="login-btn"
              size="medium"
              type="primary"
              style="width: 100%"
              @click.native.prevent="handleLogin"
            >
              <span v-if="!loading">登 录</span>
              <span v-else>登录中...</span>
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator'
import { Route } from 'vue-router'
import { Form as ElForm, Input } from 'element-ui'
import { UserModule } from '@/store/modules/user'
import { isValidUsername } from '@/utils/validate'

@Component({
  name: 'Login',
})
export default class extends Vue {
  private validateUsername = (rule: any, value: string, callback: Function) => {
    if (!value) {
      callback(new Error('请输入用户名'))
    } else {
      callback()
    }
  }
  private validatePassword = (rule: any, value: string, callback: Function) => {
    if (value.length < 6) {
      callback(new Error('密码必须在6位以上'))
    } else {
      callback()
    }
  }
  private loginForm = {
    username: 'admin',
    password: '123456',
  } as {
    username: String
    password: String
  }

  loginRules = {
    username: [{ validator: this.validateUsername, trigger: 'blur' }],
    password: [{ validator: this.validatePassword, trigger: 'blur' }],
  }
  private loading = false
  private redirect?: string

  @Watch('$route', { immediate: true })
  private onRouteChange(route: Route) {}

  // 登录
  private handleLogin() {
    ;(this.$refs.loginForm as ElForm).validate(async (valid: boolean) => {
      if (valid) {
        this.loading = true
        await UserModule.Login(this.loginForm as any)
          .then((res: any) => {
            if (String(res.code) === '1') {
              this.$router.push('/')
            } else {
              this.loading = false
            }
          })
          .catch(() => {
            this.loading = false
          })
      } else {
        return false
      }
    })
  }
}
</script>

<style lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background: linear-gradient(135deg, #2a2a2e 0%, #3d3333 50%, #2a2a2e 100%);
}

.login-box {
  width: 900px;
  height: 480px;
  border-radius: 12px;
  display: flex;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.35);
}

.login-visual {
  width: 55%;
  height: 100%;
  flex-shrink: 0;
  .visual-bg {
    width: 100%;
    height: 100%;
    display: block;
  }
}

.login-form {
  background: #ffffff;
  width: 45%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  .el-form {
    width: 260px;
  }
  .el-form-item {
    margin-bottom: 28px;
  }
  .el-form-item.is-error .el-input__inner {
    border: 0 !important;
    border-bottom: 1px solid #b22222 !important;
    background: #fff !important;
  }
  .el-input__inner {
    border: 0;
    border-bottom: 1px solid #e0e0e0;
    border-radius: 0;
    font-size: 13px;
    font-weight: 400;
    color: #333333;
    height: 38px;
    line-height: 38px;
    transition: border-color 0.3s;
  }
  .el-input__inner:focus {
    border-bottom-color: #b22222;
  }
  .el-input__prefix {
    left: 0;
    color: #b22222;
  }
  .el-input--prefix .el-input__inner {
    padding-left: 28px;
  }
  .el-input__inner::placeholder {
    color: #b0b0b0;
  }
  .el-form-item--medium .el-form-item__content {
    line-height: 38px;
  }
  .el-input--medium .el-input__icon {
    line-height: 38px;
  }
}

.login-subtitle {
  text-align: center;
  color: #999;
  font-size: 13px;
  margin: 0 0 32px 0;
  letter-spacing: 2px;
}

.login-btn {
  border-radius: 20px;
  padding: 12px 20px !important;
  margin-top: 8px;
  font-weight: 500;
  font-size: 14px;
  letter-spacing: 4px;
  border: 0;
  color: #ffffff;
  background-color: #b22222;
  box-shadow: 0 4px 12px rgba(178, 34, 34, 0.3);
  transition: all 0.3s;
  &:hover,
  &:focus {
    background-color: #c43e40;
    color: #ffffff;
    box-shadow: 0 6px 16px rgba(178, 34, 34, 0.4);
  }
}
.login-form-title {
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 12px;
}
</style>
