<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

class UserModel extends PersonModel implements User {
    /**
     * @var string
     */
    private $role;

    /**
     * @return string
     */
    public function getRole() { return $this->role; }
}
